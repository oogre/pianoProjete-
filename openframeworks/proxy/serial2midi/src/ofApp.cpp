#include "ofApp.h"

//--------------------------------------------------------------
void ofApp::setup()
{
    if( serial.setup(0, 9600))
    {
        serial.flush();
    }
    midiOut.openPort(0);
}

//--------------------------------------------------------------
void ofApp::update()
{
    if(lookingForHeader && serial.available() > 0)
    {
        message = serial.readByte();
        MSB = message & 0xF0;
        LSB = message & 0x0F;
        if(MSB == MIDI_NOTE_ON || MSB == MIDI_NOTE_OFF){
            lookingForHeader = false;
        }
    }
    if (!lookingForHeader && serial.available() >= 2)
    {
        midiOut << StartMidi() << message << serial.readByte() << serial.readByte() << FinishMidi();
        lookingForHeader = true;
    }
}

//--------------------------------------------------------------
void ofApp::draw(){
    
}

//--------------------------------------------------------------
void ofApp::exit(){
    serial.close();
    midiOut.closePort();
}