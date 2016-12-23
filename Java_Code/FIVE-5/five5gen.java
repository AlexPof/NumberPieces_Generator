import java.applet.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.lang.*;
import java.net.*;
import java.math.*;

/// Générateur de sorties MIDI pour Five5 de John Cage
/// Instrument 1 : flute - 2 : clarinette en Si bémol - 3 : idem - 4 : clarinette basse - 5 : percussion

public class five5gen 
{ 
	
	static Vector P1,P2,P3,P4,P5;
	
	public static void main(String[] args) 
	{ 
		MidiFileWriter theMFW;
		int i;
		MidiSoundEvent theSE;
		
		for (i=0;i<100;i++) {
		
		partB1();
		partB2();
		partB3();
		partB4();
		partB5();
				
		theMFW = new MidiFileWriter("Output-"+(i+1)+".mid");
		//theMFW = new MidiFileWriter("Output.mid");
		theMFW.openFile();
		theMFW.writeHeader();
		theMFW.writeTrack(P1);
		theMFW.writeTrack(P2);
		theMFW.writeTrack(P3);
		theMFW.writeTrack(P4);
		theMFW.writeTrack(P5);
		theMFW.closeFile();		
		}
	} 
	
	public static void addNote (int start, int end, int nn, Vector theVect) {
		MidiSoundEvent theSE;
		
		theSE = new MidiSoundEvent(0.1*(double)start,1,nn);
		theVect.add(theSE);
		theSE = new MidiSoundEvent(0.1*(double)end,0,nn);
		theVect.add(theSE);
	}
	
	
	
	////// PARTIE 1 : FLUTE /////
	
	
	public static void partB1() {
		int i,tPrec;
		int TB[][];
		int resTB[][];
		double dur;
		
		MidiSoundEvent theSE;
		
		TB = new int[7][4];
		resTB = new int[7][2];
		
		TB[0]= new int[] {0,300,200,500};
		TB[1]= new int[] {400,600+100,600,600+300};
		TB[2]= new int[] {600+200,600+500,600+400,1200+100};
		TB[3]= new int[] {1200+50,1200+200,1200+150,1200+300};
		TB[4]= new int[] {1200+150,1800,1200+450,1800+300};
		TB[5]= new int[] {1800+150,2400,1800+450,2400+300};
		TB[6]= new int[] {2400+350,2400+500,2400+450,3000};

		tPrec=0;
		for (i=0;i<7;i++) {
			chooseTB(resTB[i],TB[i],tPrec);
			tPrec=resTB[i][1];
		}
		
		P1 = new Vector();
		
		/////// TB1
		
		addNote(resTB[0][0],resTB[0][1],63,P1);
				
		/////// TB2
		
		addNote(resTB[1][0],resTB[1][1],73,P1);
		
		/////// TB3
				
		addNote(resTB[2][0],resTB[2][1],67,P1);
		
		/////// TB4
		
		addNote(resTB[3][0],resTB[3][1],84,P1);
		
		/////// TB5
		
		addNote(resTB[4][0],resTB[4][1],72,P1);
				
		/////// TB6
		
		addNote(resTB[5][0],resTB[5][1],66,P1);

		/////// TB7
		
		addNote(resTB[6][0],resTB[6][1],80,P1);

		/// Fin

	}
	
	
	////// PARTIE 2 : CLARINETTE 1 en Sib /////

	
	public static void partB2() {
		int i,tPrec;
		int TB[][];
		int resTB[][];
		double dur;
		
		TB = new int[5][4];
		resTB = new int[5][2];
		
		TB[0]= new int[] {0,300,200,500};
		TB[1]= new int[] {400,600+100,600,600+300};
		TB[2]= new int[] {600+150,1200,600+450,1200+300};
		TB[3]= new int[] {1200+450,1800+300,1800+150,2400};
		TB[4]= new int[] {1800+450,2400+300,2400+150,3000};
		
		
		tPrec=0;
		for (i=0;i<5;i++) {
			chooseTB(resTB[i],TB[i],tPrec);
			tPrec=resTB[i][1];
		}
		
		P2 = new Vector();
		
		/////// TB1
		
		addNote(resTB[0][0],resTB[0][1],65,P2);
		
		/////// TB2
		
		addNote(resTB[1][0],resTB[1][1],72,P2);
		
		/////// TB3
		
		addNote(resTB[2][0],resTB[2][1],78,P2);
		
		/////// TB4
		
		addNote(resTB[3][0],resTB[3][1],59,P2);
		
		/////// TB5
		
		addNote(resTB[4][0],resTB[4][1],83,P2);
		
		/// Fin
	}
	
	
	////// PARTIE 3 : CLARINETTE 2 en Sib /////
	
	
	public static void partB3() {
		int i,tPrec;
		int TB[][];
		int resTB[][];
		double dur;
		
		TB = new int[6][4];
		resTB = new int[6][2];
		
		TB[0]= new int[] {0,450,300,750};
		TB[1]= new int[] {600,600+450,600+300,1200+150};
		TB[2]= new int[] {1200+300,1800,1200+500,1800+200};
		TB[3]= new int[] {1800+150,1800+300,1800+250,1800+400};
		TB[4]= new int[] {1800+300,2400,1800+500,2400+200};
		TB[5]= new int[] {2400+100,2400+400,2400+300,3000};
		
		tPrec=0;
		for (i=0;i<6;i++) {
			chooseTB(resTB[i],TB[i],tPrec);
			tPrec=resTB[i][1];
		}
		
		P3 = new Vector();
		
		/////// TB1
		
		addNote(resTB[0][0],resTB[0][1],77,P3);
		
		/////// TB2
		
		addNote(resTB[1][0],resTB[1][1],69,P3);
		
		/////// TB3
		
		addNote(resTB[2][0],resTB[2][1],82,P3);
		
		/////// TB4
		
		addNote(resTB[3][0],resTB[3][1],66,P3);
		
		/////// TB5
		
		addNote(resTB[4][0],resTB[4][1],55,P3);
		
		/////// TB5
		
		addNote(resTB[5][0],resTB[5][1],75,P3);

		/// Fin
	}

	
	////// PARTIE 4 : CLARINETTE BASSE en Sib /////

	
	public static void partB4() {
		int i,tPrec;
		int TB[][];
		int resTB[][];
		double dur;
		
		TB = new int[6][4];
		resTB = new int[6][2];
		
		TB[0]= new int[] {0,450,300,750};
		TB[1]= new int[] {600+50,600+350,600+250,600+550};
		TB[2]= new int[] {600+400,1200+250,1200+210,1200+550};
		TB[3]= new int[] {1200+500,1800+50,1800,1800+150};
		TB[4]= new int[] {1800+50,1800+500,1800+350,2400+200};
		TB[5]= new int[] {2400+100,2400+400,2400+300,3000};
		
		tPrec=0;
		for (i=0;i<6;i++) {
			chooseTB(resTB[i],TB[i],tPrec);
			tPrec=resTB[i][1];
		}
		
		P4 = new Vector();
		
		/////// TB1
		
		addNote(resTB[0][0],resTB[0][1],49,P4);
		
		/////// TB2
		
		addNote(resTB[1][0],resTB[1][1],50,P4);
		
		/////// TB3
		
		addNote(resTB[2][0],resTB[2][1],53,P4);
		
		/////// TB4
		
		addNote(resTB[3][0],resTB[3][1],52,P4);
		
		/////// TB5
		
		addNote(resTB[4][0],resTB[4][1],49,P4);
		
		/////// TB6
		
		addNote(resTB[5][0],resTB[5][1],47,P4);
		
		/// Fin
	}
	
	
	////// PARTIE 5 : PERCUSSION /////

	
	public static void partB5() {
		int i,tPrec;
		int TB[][];
		int resTB[][];
		double dur;
		
		MidiSoundEvent theSE;
		
		TB = new int[7][4];
		resTB = new int[7][2];
		
		TB[0]= new int[] {0,150,100,250};
		TB[1]= new int[] {450,600+300,600+150,1200};
		TB[2]= new int[] {600+450,1200+300,1200+150,1800};
		TB[3]= new int[] {1200+550,1800+100,1800+50,1800+200};
		TB[4]= new int[] {1800+150,1800+300,1800+250,1800+400};
		TB[5]= new int[] {1800+300,2400,1800+500,2400+200};
		TB[6]= new int[] {2400+100,2400+400,2400+300,3000};
		
		tPrec=0;
		for (i=0;i<7;i++) {
			chooseTB(resTB[i],TB[i],tPrec);
			tPrec=resTB[i][1];
		}
		
		P5 = new Vector();
		
		/////// TB1
		
		addNote(resTB[0][0],resTB[0][1],60,P5);
		
		/////// TB2
		
		addNote(resTB[1][0],resTB[1][1],71,P5);
		
		/////// TB3
		
		addNote(resTB[2][0],resTB[2][1],67,P5);
		
		/////// TB4
		
		addNote(resTB[3][0],resTB[3][1],72,P5);
		
		/////// TB5
		
		addNote(resTB[4][0],resTB[4][1],64,P5);
		
		/////// TB6
		
		addNote(resTB[5][0],resTB[5][1],64,P5);
		
		/////// TB7
		
		addNote(resTB[6][0],resTB[6][1],72,P5);
		
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