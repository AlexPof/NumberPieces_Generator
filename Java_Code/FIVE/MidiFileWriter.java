import java.applet.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.lang.*;
import java.net.*;
import java.math.*;


public class MidiFileWriter {
	String fileName;
	File theFile;
	RandomAccessFile theRAF;
	
	static int numTracks;
	
	public MidiFileWriter(String theFilePathName) {
		this.fileName = theFilePathName;
		numTracks = 0;
	}
	
	public void openFile() {
		try {
			theFile = new File(fileName);
			theRAF = new RandomAccessFile(theFile,"rw");
		}
		catch (Exception e) {
			System.out.println("Error opening the file");
			System.out.println(e.getMessage());
		}
	}
	
	public void writeHeader() {
		try {
		theRAF.write('M');theRAF.write('T');theRAF.write('h');theRAF.write('d');
		theRAF.writeInt(6); // Length of the header
		theRAF.writeShort(1); // MIDI type 1 : multitrack, synchronous
		theRAF.writeShort(0); // Number of tracks, 0 for now
		theRAF.writeShort(120); // 120 ticks per quarter
		} catch (Exception e) {
			System.out.println("Error writing the header");
			System.out.println(e.getMessage());
		}
	}
	
	public void writeTrack (Vector listeSE) {
		long pointerPos,pointerPos2,deltaTime;
		int byteCounter,i;
		double prevT;
		byte[] dtByte;
		MidiSoundEvent theSE;
		
		numTracks=numTracks+1;
		
		sortTimeList (listeSE);
		
		try {
		theRAF.write('M');theRAF.write('T');theRAF.write('r');theRAF.write('k');
		pointerPos = theRAF.getFilePointer(); // We save the position of the file, as we will have to write the length later
		theRAF.writeInt(0); // We don't know the length of the track chunk yet

			// Adding the tempo information, 120bpm in our case
			
		theRAF.write(0);theRAF.write(0xFF);theRAF.write(0x51);theRAF.write(0x03);
		theRAF.write(0x07);theRAF.write(0xA1);theRAF.write(0x20);
			
			// Adding a fake note right at the beginning of the track, so that we clearly define 0 time
			
		theRAF.write(0);theRAF.write(0x90);theRAF.write(0);theRAF.write(40);
		theRAF.write(1);theRAF.write(0x80);theRAF.write(0);theRAF.write(40);
			
	    prevT = 0.0; byteCounter = 15;
			
		for (i=0;i<listeSE.size();i++) {
			
			theSE = (MidiSoundEvent)(listeSE.elementAt(i));
			deltaTime = (long)(240*(theSE.time-prevT));
			dtByte = encodeVL(deltaTime);
			prevT = theSE.time;
			
			byteCounter += dtByte.length;
			theRAF.write(dtByte);
			
			if (theSE.type==1)
				theRAF.write(0x90);
			else
				theRAF.write(0x80);
			
			theRAF.write(theSE.noteNumber);
			theRAF.write(0x40);
			byteCounter += 3;
		}
			// Writing the "end of track" MIDI event
		theRAF.write(0x00);theRAF.write(0xFF);theRAF.write(0x2F);theRAF.write(0x00);
		byteCounter += 4;
			
		pointerPos2 = theRAF.getFilePointer();
		theRAF.seek(pointerPos);
		theRAF.writeInt(byteCounter);
		theRAF.seek(pointerPos2);
		} catch (Exception e) {
			System.out.println("Error writing the track");
			System.out.println(e.getMessage());
		}
	}
	
	public void closeFile() {
		// We have to write the number of tracks before closing the file
		
		try {
		theRAF.seek(10);
		theRAF.writeShort(numTracks);
		theRAF.close();
		} catch (Exception e) {
			System.out.println("Error closing the file");
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	
	
	///// Sorts a list of sound events according to their time
	
	public void sortTimeList (Vector totalList) {
		MidiSoundEvent theSE,theSE2;
		int p,N;
		
		p=0; N=totalList.size();
		while (p<N-1) {
			theSE = (MidiSoundEvent)totalList.elementAt(p);
			p=p+1;
			theSE2 = (MidiSoundEvent)totalList.elementAt(p);
			
			if (theSE2.time<theSE.time) {
				totalList.removeElementAt(p);
				p=p-1;
				totalList.add(p,theSE2);
				if (p>0)
					p=p-1;
			}
			
		}		
	}
	
	
	///// Adjust a list of sound events so that it begins at time 0
	
	public void adjustTimeList (Vector totalList) {
		double minTime=50000; // 50000 seconds seems quite a reasonable max time
		int i;
		MidiSoundEvent theSE;
		
		for (i=0;i<totalList.size();i++) {
			theSE = (MidiSoundEvent)(totalList.elementAt(i));
			if (theSE.time<minTime)
				minTime = theSE.time;
		}
		for (i=0;i<totalList.size();i++) {
			theSE = (MidiSoundEvent)(totalList.elementAt(i));
			theSE.time-=minTime;
		}
	}

	
	
	//// Encodes a long value in an array of bytes, variable-length format
	
	public static byte[] encodeVL(long n)
	{
		int numRelevantBits = 64 - Long.numberOfLeadingZeros(n);
		int numBytes = (numRelevantBits + 6) / 7;
		if (numBytes == 0)
			numBytes = 1;
		byte[] output = new byte[numBytes];
		for (int i = numBytes - 1; i >= 0; i--)
		{
			int curByte = (int)(n & 0x7F);
			if (i != (numBytes - 1))
				curByte |= 0x80;
			output[i] = (byte)curByte;
			n >>>= 7;
		}
		return output;
	}
	
}
