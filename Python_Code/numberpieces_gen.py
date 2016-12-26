## coding: utf-8

import struct
import json
import numpy as np

################## MIDI-RELATED FUNCTIONS

#########
### Variable-length encoding

def encodeVL(theint):
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
	
def writeMIDI(filename,data):
	with open("./"+filename,"wb") as f:

		## Writing the MIDI file header
		f.write("MThd")
		f.write(struct.pack(">ihhh",6,1,len(d["tracks"]),120)) # Length of the header, MIDI type 1, number of track, 120 ticks per quarter

		for x in data["tracks"]:
			trackdata = [[y["type"],y["note"],y["velocity"],y["time"]] for y in x]
			trackdata = sorted(trackdata, key=lambda x:x[3])
			
			trackdata_diff = [[trackdata[0][0],trackdata[0][1],trackdata[0][2],trackdata[0][3]]]
			for i in range(1,len(trackdata)):
				trackdata_diff.append([trackdata[i][0],trackdata[i][1],trackdata[i][2],trackdata[i][3]-trackdata[i-1][3]])
			trackdata_diff = [[x[0],x[1],x[2],encodeVL(int(240.*x[3]))] for x in trackdata_diff]
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

with open("five_data.txt","r") as jsonfile:
	numberpiece = json.load(jsonfile)
	
## Generating the Number Piece

tracks_list = []
for player in numberpiece["players"]:
	track = []
	
	## We process the time-brackets sequentially
	
	t_prec=0.0
	for tb in player["timebrackets"]:
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
				track.append({"type":"ON","note":tb["note"][i],"velocity":40,"time":cue_times[i]/10.0})
				track.append({"type":"OFF","note":tb["note"][i],"velocity":40,"time":cue_times[i+1]/10.0})
		t_prec = end_time
	tracks_list.append(track)
	
d = {"tracks":tracks_list}

writeMIDI("essai2.mid",d)
