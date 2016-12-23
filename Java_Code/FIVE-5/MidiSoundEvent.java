import java.applet.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.lang.*;
import java.net.*;
import java.math.*;


public class MidiSoundEvent {

	double time;
	int type;
	int noteNumber;
	
	public MidiSoundEvent(double t, int isON, int nn) {
		this.time = t;
		this.type = isON;
		this.noteNumber = nn;
	}
}
