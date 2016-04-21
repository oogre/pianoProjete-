#pragma once

#include "ofMain.h"
#include "ofxMidi.h"


#define MIDI_NOTE_ON 0x090
#define MIDI_NOTE_OFF 0x080
class ofApp : public ofBaseApp{

	public:
		void setup();
		void update();
		void draw();
        void exit();
		
   	private:
        ofSerial serial;
        ofxMidiOut midiOut;
        bool lookingForHeader = true;
        int message;
        int MSB;
        int LSB;
};
