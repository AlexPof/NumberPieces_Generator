## coding: utf-8

import struct
import json
import numpy as np

################## MIDI-RELATED FUNCTIONS

#########
### Variable-length encoding

def encodeVL(theint):
	"""
		Encode an int as a variable-length format integer
		
		Inputs:
		- theint: integer to be encoded
		Outputs:
		- a list of bytes encoding the integer in variable-length format
	
	"""

	for i in range(64)[::-1]:
		if ((theint>>i)&1):
			break
	numRelevantBits = i+1
	numBytes = (numRelevantBits + 6) / 7
	if (numBytes == 0):
		numBytes=1
	output=[]
	for i in range(numBytes)[::-1]:
		curByte = (int)(theint & 0x7F)
		
		if not (i == (numBytes - 1)):
			curByte |= 0x80
		output.append(curByte)
		theint >>= 7
	return output[::-1]
	
def writeMIDI(filepath,data):
	"""
	
		General purpose function for generating MIDI file from real time-valued sound events data
		
		Inputs:
		- filepath: path of the MIDI file to be written (should include ".mid")
		- data: a dictionary containing multiple tracks, each track containing real time-valued sound events.
				The dictionary has one key, "tracks", whose value is a list of lists.
				Each list represents one track in the final MIDI file.
				The elements of each list are dictionaries representing a single sound event, each dictionary having the following keys:
					- "type": "ON" or "OFF" (MIDI events 0x90 or 0x80)
					- "note": integer value representing the note pitch
					- "velocity": integer value representing the note velocity
					- "time": float value representing the time in seconds
					
				Example:
				
				{
					"tracks": [ [{"type":"OFF","note":62,"velocity":40,"time":5.067},
								 {"type":"ON","note":62,"velocity":40,"time":2.067},
								 {"type":"ON","note":62,"velocity":40,"time":7.067},
								 {"type":"OFF","note":62,"velocity":40,"time":8.067}]
								 ,
								[{"type":"OFF","note":67,"velocity":40,"time":6.067},
								 {"type":"ON","note":67,"velocity":40,"time":1.067},
								 {"type":"ON","note":72,"velocity":40,"time":6.567},
								 {"type":"OFF","note":72,"velocity":40,"time":12.067}]
							   ]
				}
					
		Outputs:
		- None. A MIDI file is written at filepath.
	
	"""


	with open(filepath,"wb") as f:

		## Writing the MIDI file header
		f.write("MThd")
		f.write(struct.pack(">ihhh",6,1,len(d["tracks"]),120)) # Length of the header, MIDI type 1, number of track, 120 ticks per quarter

		for x in data["tracks"]:
			# Reordering all the sound events by increasing time
			trackdata = [[y["type"],y["note"],y["velocity"],y["time"]] for y in x]
			trackdata = sorted(trackdata, key=lambda x:x[3])
			
			# MIDI files deal with time differences, which we calculate here
			trackdata_diff = [[trackdata[0][0],trackdata[0][1],trackdata[0][2],trackdata[0][3]]]
			for i in range(1,len(trackdata)):
				trackdata_diff.append([trackdata[i][0],trackdata[i][1],trackdata[i][2],trackdata[i][3]-trackdata[i-1][3]])
			trackdata_diff = [[x[0],x[1],x[2],encodeVL(int(240.*x[3]))] for x in trackdata_diff]
			
			# Number of bytes of the track chunk: 15 for standard info (120bpm, fake notes, etc.), 4 for the tail, the rest depends on the data
			trackdata_numbytes = 15+4+3*len(trackdata_diff)+sum([len(x[3]) for x in trackdata_diff])
			
			
			## Writing the track chunk to the MIDI file
			f.write("MTrk")
			f.write(struct.pack(">i",trackdata_numbytes)) # Length of the track chunk

			# 120 bpm
			f.write(struct.pack(">BBBB",0,0xFF,0x51,0x03))
			f.write(struct.pack(">BBB",0x07,0xA1,0x20))

			# Fake note at the beginning to mark 0 time
			f.write(struct.pack(">BBBB",0,0x90,0,40))
			f.write(struct.pack(">BBBB",1,0x80,0,40))
			
			# Writing one note
			for x in trackdata_diff:
				for y in x[3]:
					f.write(struct.pack(">B",y))
				if x[0]=="ON":
					f.write(struct.pack(">BBB",0x90,x[1],x[2]))
				if x[0]=="OFF":
					f.write(struct.pack(">BBB",0x80,x[1],x[2]))
			
			## End of the track chunk
			f.write(struct.pack(">BBBB",0,0xFF,0x2F,0))


################## NUMBER PIECES-RELATED FUNCTIONS


def pick_time(time_1,time_2,type="uniform"):
	"""
	
		Picks a float-valued time between time_1 and time_2 according to the given picking type
		
		Inputs:
		- time_1, time_2: float time values (with time_1<time_2)
		- type: string indicating how the time value should be picked
				- "uniform" (default): the time value is sampled uniformly between time_1 and time_2
				- "gaussian": the time value is sampled according to a gaussian distribution centered at (time_1+time_2)/2
							  with standard deviation (time_2-time_1)/2
					
		Outputs:
		- A float-valued time
	
	"""
	if type=="uniform":
		return np.random.uniform(low=time_1,high=time_2)
	if type=="gaussian":
		center = 0.5*(time_2-time_1);
		sig = 0.5*(time_2-center);
		t = np.random.uniform(low=time_1,high=time_2)
		h = np.exp(-((t-center)**2)/(2.0*sig**2))
		while (np.random.rand()>h):
			t = np.random.uniform(low=time_1,high=time_2)
			h = np.exp(-((t-center)**2)/(2.0*sig**2))
		return t

def chooseTB(limits,t_prec,type="uniform"):
	"""
	
		Select the starting and ending times of a given time-brackets
		
		Inputs:
		- limits: a list of 4 floats : [starting time interval beginning, starting time interval end, ending time interval beginning, ending time interval end]
		- t_prec: the ending time value of the previous sound event, in case it falls inside the starting time interval of the specified time bracket
		- type: string indicating how the time value should be picked
		
		Outputs:
		- a pair (starting time, ending time) representing the beginning and end of the sound event for the specified time-bracket
	
	"""

	if t_prec>limits[0]:
		st = pick_time(t_prec,limits[1],type)
	else:
		st = pick_time(limits[0],limits[1],type)

	if st>limits[2]:
		et = pick_time(st,limits[3],type)
	else:
		et = pick_time(limits[2],limits[3],type)

	return (st,et)


################## MAIN

"""

	Data files encoding the Number Pieces are JSON 
	
	The root key of the JSON is "players", whose value is a list of dictionaries.
				Each dictionary represents one of the player of the Number Piece.
				Each dictionary has the following keys and values:
					- "name": string indicating the name of the player (example: "Clarinet 1", "Unspecified instrument 1", etc.)
					- "timebrackets": list of dictionaries, each of which represents a time-bracket and has the following keys:
						- "note": list of integers representing the notes pitches (percussion instruments are assumed
								  to be assigned specific pitches on a MIDI keyboard) inside the given time-bracket.
								  -1 indicates a pause between notes.
						- "limits": list of 4 values in seconds: [starting time interval beginning, starting time interval end, ending time interval beginning, ending time interval end]


"""


with open("five_data.txt","r") as jsonfile:
	numberpiece = json.load(jsonfile)
	
## Generating the Number Piece

tracks_list = []
for player in numberpiece["players"]:
	track = []
	
	## We process the time-brackets sequentially
	
	t_prec=0.0
	for tb in player["timebrackets"]:
		## Check if the time-bracket is still playable
		if t_prec<tb["limits"][1]:
			start_time,end_time = chooseTB(tb["limits"],t_prec,type="gaussian")
			
			## Generating the intermediate cue times successively, in case multiple notes are present in a time-bracket
			cue_times=[start_time,end_time]
			num_notes = len(tb["note"])
			c=start_time
			for i in range(num_notes-1):
				cue_times.append(pick_time(c,end_time,type="uniform"))
			cue_times = sorted(cue_times)
			
			for i in range(num_notes):
				## -1 indicates a pause (silence)
				if tb["note"][i]>-1:
					track.append({"type":"ON","note":tb["note"][i],"velocity":40,"time":cue_times[i]})
					track.append({"type":"OFF","note":tb["note"][i],"velocity":40,"time":cue_times[i+1]})
			t_prec = end_time
	tracks_list.append(track)
	
d = {"tracks":tracks_list}

writeMIDI("example_realization.mid",d)
