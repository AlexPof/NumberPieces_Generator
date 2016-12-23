import java.applet.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.lang.*;
import java.net.*;
import java.math.*;


/// Generateur de fichiers MIDI pour Five de John Cage
/// Instrument 1 : Flute - 2 : Violon - 3 : Clarinette - 4 : Violon - 5 : Clarinette

public class fivegen 
{ 
	
	static Vector P1,P2,P3,P4,P5;
	
	public static void main(String[] args) 
	{ 
		MidiFileWriter theMFW;
		int i;
		MidiSoundEvent theSE;
		
//		for (i=0;i<70;i++) {
		
		partB1();
		partB2();
		partB3();
		partB4();
		partB5();
				
//		theMFW = new MidiFileWriter("Output"+(i+31)+".mid");
		theMFW = new MidiFileWriter("Output.mid");
		theMFW.openFile();
		theMFW.writeHeader();
		theMFW.writeTrack(P1);
		theMFW.writeTrack(P2);
		theMFW.writeTrack(P3);
		theMFW.writeTrack(P4);
		theMFW.writeTrack(P5);
		theMFW.closeFile();		
//		}
	} 
	
	public static void addNote (int start, int end, int nn, Vector theVect) {
		MidiSoundEvent theSE;
		
		theSE = new MidiSoundEvent(0.1*(double)start,1,nn);
		theVect.add(theSE);
		theSE = new MidiSoundEvent(0.1*(double)end,0,nn);
		theVect.add(theSE);
	}
	
	public static void partB1() {
		int i,tPrec,r1,r2,r3;
		int TB[][];
		int resTB[][];
		double dur;
		
		MidiSoundEvent theSE;
		
		TB = new int[5][4];
		resTB = new int[5][2];
		
		TB[0]= new int[] {0,450,300,750};
		TB[1]= new int[] {600,600+450,600+300,1200+150};
		TB[2]= new int[] {1200+150,1200+150,1200+450,1200+450};
		TB[3]= new int[] {1200+450,1800+300,1800+150,2400};
		TB[4]= new int[] {1800+450,2400+300,2400+150,3000};


		tPrec=0;
		for (i=0;i<5;i++) {
			chooseTB(resTB[i],TB[i],tPrec);
			tPrec=resTB[i][1];
		}
		
		P1 = new Vector();
		
		/////// TB1
		
		addNote(resTB[0][0],resTB[0][1],80,P1);
				
		/////// TB2
		
		addNote(resTB[1][0],resTB[1][1],73,P1);
		
		/////// TB3
				
		r1=intRand(resTB[2][0],resTB[2][1]);
		r2=intRand(r1,resTB[2][1]);
		r3=intRand(r2,resTB[2][1]);
		
		
		addNote(resTB[2][0],r1,80,P1);		
		
		addNote(r2,r3,62,P1);

		addNote(r3,resTB[2][1],84,P1);
		
		/////// TB4
		
		addNote(resTB[3][0],resTB[3][1],74,P1);
		
		/////// TB5
		
		addNote(resTB[4][0],resTB[4][1],67,P1);
				
		/// Fin

	}
	
	public static void partB2() {
		int i,tPrec,r1,r2,r3;
		int TB[][];
		int resTB[][];
		double dur;
		
		TB = new int[5][4];
		resTB = new int[5][2];
		
		TB[0]= new int[] {0,450,300,750};
		TB[1]= new int[] {600,600+450,600+300,1200+150};
		TB[2]= new int[] {1200+150,1200+150,1200+450,1200+450};
		TB[3]= new int[] {1200+450,1800+300,1800+150,2400};
		TB[4]= new int[] {1800+450,2400+300,2400+150,3000};
		
		
		tPrec=0;
		for (i=0;i<5;i++) {
			chooseTB(resTB[i],TB[i],tPrec);
			tPrec=resTB[i][1];
		}
		
		P2 = new Vector();
		
		/////// TB1
		
		r1=intRand(resTB[0][0],resTB[0][1]);
		r2=intRand(r1,resTB[0][1]);
		
		addNote(resTB[0][0],r1,68,P2);
		
		addNote(r2,resTB[0][1],71,P2);

		/////// TB2
		r1=intRand(resTB[1][0],resTB[1][1]);
		r2=intRand(r1,resTB[1][1]);
		
		addNote(resTB[1][0],r1,68,P2);
		
		addNote(r2,resTB[1][1],76,P2);
		
		/////// TB3
		
		addNote(resTB[2][0],resTB[2][1],65,P2);
		
		/////// TB4
		
		addNote(resTB[3][0],resTB[3][1],73,P2);
		
		/////// TB5
		
		
		r1=intRand(resTB[4][0],resTB[4][1]);
		r2=intRand(r1,resTB[4][1]);
		r3=intRand(r2,resTB[4][1]);
		
		
		addNote(resTB[4][0],r1,64,P2);
		
		addNote(r2,r3,65,P2);
		
		addNote(r3,resTB[4][1],60,P2);
		
		/// Fin
	}
	
	public static void partB3() {
		int i,tPrec,r1,r2;
		int TB[][];
		int resTB[][];
		double dur;
		
		TB = new int[5][4];
		resTB = new int[5][2];
		
		TB[0]= new int[] {0,450,300,750};
		TB[1]= new int[] {600,600+450,600+300,1200+150};
		TB[2]= new int[] {1200+150,1200+150,1200+450,1200+450};
		TB[3]= new int[] {1200+450,1800+300,1800+150,2400};
		TB[4]= new int[] {1800+450,2400+300,2400+150,3000};
		
		
		tPrec=0;
		for (i=0;i<5;i++) {
			chooseTB(resTB[i],TB[i],tPrec);
			tPrec=resTB[i][1];
		}
		
		P3 = new Vector();
		
		//////// TB1
		
		addNote(resTB[0][0],resTB[0][1],65,P3);
		
		//////// TB2
		
		r1=intRand(resTB[1][0],resTB[1][1]);
		
		addNote(resTB[1][0],r1,63,P3);
				
		addNote(r1,resTB[1][1],67,P3);
		
		//////// TB3
		addNote(resTB[2][0],resTB[2][1],68,P3);
		
		//////// TB4
		addNote(resTB[3][0],resTB[3][1],70,P3);
		
		
		//////// TB5

		r1=intRand(resTB[4][0],resTB[4][1]);
		r2=intRand(r1,resTB[4][1]);

		addNote(resTB[4][0],r1,53,P3);
		
		addNote(r2,resTB[4][1],54,P3);
		
		/// Fin
}
	
	public static void partB4() {
		int i,tPrec,r1,r2,r3,r4,r5;
		int TB[][];
		int resTB[][];
		double dur;
		
		TB = new int[5][4];
		resTB = new int[5][2];
		
		TB[0]= new int[] {0,450,300,750};
		TB[1]= new int[] {600,600+450,600+300,1200+150};
		TB[2]= new int[] {1200+150,1200+150,1200+450,1200+450};
		TB[3]= new int[] {1200+450,1800+300,1800+150,2400};
		TB[4]= new int[] {1800+450,2400+300,2400+150,3000};
		
		
		tPrec=0;
		for (i=0;i<5;i++) {
			chooseTB(resTB[i],TB[i],tPrec);
			tPrec=resTB[i][1];
		}
		
		P4 = new Vector();

		///////// TB1

		addNote(resTB[0][0],resTB[0][1],56,P4);
		
		///////// TB2
		addNote(resTB[1][0],resTB[1][1],60,P4);
		
		///////// TB3
		addNote(resTB[2][0],resTB[2][1],67,P4);
		
		///////// TB4
		addNote(resTB[3][0],resTB[3][1],62,P4);
		
		///////// TB5
		addNote(resTB[4][0],resTB[4][1],66,P4);

		/// Fin
	}
	
	public static void partB5() {
		int i,tPrec,r1,r2,r3,r4,r5;
		int TB[][];
		int resTB[][];
		double dur;
		
		TB = new int[5][4];
		resTB = new int[5][2];
		
		TB[0]= new int[] {0,450,300,750};
		TB[1]= new int[] {600,600+450,600+300,1200+150};
		TB[2]= new int[] {1200+150,1200+150,1200+450,1200+450};
		TB[3]= new int[] {1200+450,1800+300,1800+150,2400};
		TB[4]= new int[] {1800+450,2400+300,2400+150,3000};
		
		
		tPrec=0;
		for (i=0;i<5;i++) {
			chooseTB(resTB[i],TB[i],tPrec);
			tPrec=resTB[i][1];
		}
		
		P5 = new Vector();
		
		//////// TB1
		
		r1=intRand(resTB[0][0],resTB[0][1]);
		r2=intRand(r1,resTB[0][1]);
		r3=intRand(r2,resTB[0][1]);
		
		
		addNote(resTB[0][0],r1,63,P5);
		
		addNote(r2,r3,67,P5);
		
		addNote(r3,resTB[0][1],70,P5);
		
		//////// TB2

		r1=intRand(resTB[1][0],resTB[1][1]);
		
		addNote(resTB[1][0],r1,71,P5);
		
		addNote(r1,resTB[1][1],69,P5);
		
		//////// TB3
		
		r1=intRand(resTB[2][0],resTB[2][1]);
		r2=intRand(r1,resTB[2][1]);
				
		addNote(resTB[2][0],r1,52,P5);
		
		addNote(r2,resTB[2][1],71,P5);

		//////// TB4

		r1=intRand(resTB[3][0],resTB[3][1]);
		r2=intRand(r1,resTB[3][1]);
		r3=intRand(r2,resTB[3][1]);
		r4=intRand(r3,resTB[3][1]);

		addNote(resTB[3][0],r1,71,P5);
		
		addNote(r2,r3,60,P5);

		addNote(r4,resTB[3][1],61,P5);

		//////// TB5
		addNote(resTB[4][0],resTB[4][1],62,P5);
		
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
		sig = 0.5*(((double)maxInt)-center);
		
		u = Math.random()*((double)maxInt-(double)minInt)+(double)minInt;
		h = Math.exp(-(u-center)*(u-center)/(2*sig*sig));
		while (Math.random()>h) {
			u = Math.random()*((double)maxInt-(double)minInt)+(double)minInt;
			h = Math.exp(-(u-center)*(u-center)/(2*sig*sig));
		}
		
		return (int)u;
		
		//return (int)(Math.ceil(Math.random()*(maxInt-minInt))+minInt);
	}
} 