import java.applet.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.lang.*;
import java.net.*;
import java.math.*;


public class one4gen 
{ 
	
	static Vector P1,P2;
	
	public static void main(String[] args) 
	{ 
		MidiFileWriter theMFW;
		int i;
		MidiSoundEvent theSE;
		
		partB1();
		partB2();
				
		theMFW = new MidiFileWriter("Output.mid");
		theMFW.openFile();
		theMFW.writeHeader();
		theMFW.writeTrack(P1); // Track 1 is for sustained sounds
		theMFW.writeTrack(P2); // Track 2 is for damped sounds
		theMFW.closeFile();		
	} 
	
	public static void addNote (int start, int end, int nn, int nv, Vector theVect) {
		MidiSoundEvent theSE;
		
		theSE = new MidiSoundEvent(0.1*(double)start,1,nn,nv);
		theVect.add(theSE);
		theSE = new MidiSoundEvent(0.1*(double)end,0,nn,nv);
		theVect.add(theSE);
	}
	
	public static void partB1() {
		int i,tPrec;
		int TB[][];
		int resTB[][];
		double dur;
		
		MidiSoundEvent theSE;
		
		TB = new int[6][4];
		resTB = new int[6][2];
		
		TB[0]= new int[] {0,600,400,600+400};
		TB[1]= new int[] {600+250,1200+50,600+500,1200+350};
		TB[2]= new int[] {1200+200,1800+50,1200+500,1800+350};
		TB[3]= new int[] {1800+200,2400+50,1800+500,2400+350};
		TB[4]= new int[] {2400+100,3000+250,3000,3600+150};
		TB[5]= new int[] {3600+50,3600+350,3600+250,3600+550};


		tPrec=0;
		for (i=0;i<6;i++) {
			chooseTB(resTB[i],TB[i],tPrec);
			tPrec=resTB[i][1];
		}
		
		P1 = new Vector();
		
		/////// TB1
		
		addNote(resTB[0][0],resTB[0][1],64,(int)(Math.random()*127),P1);
				
		/////// TB2
		
		addNote(resTB[1][0],resTB[1][1],67,(int)(Math.random()*127),P1);
		
		/////// TB3
				
		addNote(resTB[2][0],resTB[2][1],60,(int)(Math.random()*127),P1);
		
		/////// TB4
		
		addNote(resTB[3][0],resTB[3][1],67,(int)(Math.random()*127),P1);
		
		/////// TB5
		
		addNote(resTB[4][0],resTB[4][1],71,(int)(Math.random()*127),P1);
				
		/////// TB6
		
		addNote(resTB[5][0],resTB[5][1],60,(int)(Math.random()*127),P1);

		/// Fin

	}
	
	public static void partB2() {
		int i,tPrec,r1,r2,r3;
		int TB[][];
		int resTB[][];
		double dur;
		
		TB = new int[8][4];
		resTB = new int[8][2];
		
		TB[0]= new int[] {100,150,100,150};
		TB[1]= new int[] {350,450,350,450};
		TB[2]= new int[] {600+100,600+150,600+100,600+150};
		TB[3]= new int[] {600+200,600+350,600+300,600+450};
		TB[4]= new int[] {600+550,1200+50,600+550,1200+50};
		TB[5]= new int[] {1200+450,1800+50,1200+450,1800+50};
		TB[6]= new int[] {1800+200,2400+350,2400+100,3000+250};
		TB[7]= new int[] {3000+400,3000+550,3000+400,3000+550};
		
		
		tPrec=0;
		for (i=0;i<8;i++) {
			chooseTB(resTB[i],TB[i],tPrec);
			tPrec=resTB[i][1];
		}
		
		P2 = new Vector();
		
		//// Beware : except for time-brackets 4 and 7, the rest are all very short sounds, so we take their durations as 10 seconds
		//// 
		
		/////// TB1
		
		addNote(resTB[0][0],resTB[0][0]+100,62,(int)(Math.random()*127),P2);

		/////// TB2

		addNote(resTB[1][0],resTB[1][0]+100,67,(int)(Math.random()*127),P2);

		/////// TB3
		
		addNote(resTB[2][0],resTB[2][0]+100,64,(int)(Math.random()*127),P2);
		
		/////// TB4
		
		addNote(resTB[3][0],resTB[3][1],74,(int)(Math.random()*127),P2);
		
		/////// TB5
		
		addNote(resTB[4][0],resTB[4][0]+100,71,(int)(Math.random()*127),P2);
		
		/////// TB6
		
		addNote(resTB[5][0],resTB[5][0]+100,69,(int)(Math.random()*127),P2);

		/////// TB7
		
		addNote(resTB[6][0],resTB[6][1],74,(int)(Math.random()*127),P2);

		/////// TB8
		
		addNote(resTB[7][0],resTB[7][0]+100,60,(int)(Math.random()*127),P2);

		/// Fin
	}
			
	public static void chooseTB(int[] selectedPoints,int[] timeBracket, int tPrec) {
		int ts,te;
		
		
		if (tPrec>timeBracket[0]) {
			ts=intRand(tPrec,timeBracket[1]);
		} else {
			ts=intRand(timeBracket[0],timeBracket[1]);
		}
		
		if (ts>timeBracket[2]) {
			te=intRand(ts,timeBracket[3]);
		} else {
			te=intRand(timeBracket[2],timeBracket[3]);
		}
		
		selectedPoints[0]=ts;
		selectedPoints[1]=te;
	}
	
	
	public static int intRand(int minInt, int maxInt) {
		double u,h,r,center,sig;
		
		center = 0.5*(double)(maxInt-minInt);
		sig = 0.6*(((double)maxInt)-center);
		
		u = Math.random()*((double)maxInt-(double)minInt)+(double)minInt;
		h = Math.exp(-(u-center)*(u-center)/(2*sig*sig));
		while (Math.random()>h) {
			u = Math.random()*((double)maxInt-(double)minInt)+(double)minInt;
			h = Math.exp(-(u-center)*(u-center)/(2*sig*sig));
		}
		
		return (int)u;
		
		//return (int)((int)(Math.random()*(maxInt-minInt))+minInt);
	}
} 