//
// pianoProjeté
//
// Description of the project
// Developed with [embedXcode](http://embedXcode.weebly.com)
//
// Author 		Vincent Evrard
// 				ogre
//
// Date			2016-04-13 14:16
// Version		0.1
//
// Copyright	© Vincent Evrard, 2016
// Licence		Creative Common
//
// See		 ReadMe.txt for references
//



#include <Arduino.h>
#include <arduino.h>
#include <CharlixButton.h>

// Set parameters


// Include application, user and local libraries


// Prototypes


// Define variables and constants
const uint8_t pins [] = { 2, 3, 4 };//, 5, 6, 7, 8, 9, 10, 11, 12, 13, A0, A1 };

CharlixButton cb(pins, 3);

// Add setup code
void setup()
{    
    cb.init();
}

// Add loop code
void loop()
{
    cb.update();
    cb.process();
}

/*
 
 
 balayage clavier : +- 60 Hz
 
 182 BUTTONS * 7Byte 1274 Bytes || 2002 Bytes
 88 NOTES   * 7Byte  616 Bytes
 ___________ ______________
 1890 Bytes	2618 Bytes
 */
