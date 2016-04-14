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

#define DEBUG_RATE 0

#ifdef DEBUG_RATE
    #if DEBUG_RATE
        uint32_t _debugRateTime0;
        uint32_t _debugRateTime1;
        uint32_t _rateTime0;
        uint32_t _rateTime1;
    #endif
#endif

const uint8_t pins [] = { 2, 3, 4 };//, 5, 6, 7, 8, 9, 10, 11, 12, 13, A0, A1 };
CharlixButton cb(pins, 3);


void setup()
{    
    cb.init();
}


void loop()
{
    #ifdef DEBUG_RATE
        #if DEBUG_RATE
            _rateTime0 = millis();
        #endif
    #endif
    
    cb.update();
    cb.process();
    
    #ifdef DEBUG_RATE
        #if DEBUG_RATE
            _rateTime1 = _debugRateTime1 = millis();
            if( _debugRateTime1 - _debugRateTime0 > DEBUG_RATE)
            {
                _debugRateTime0 = millis();
                Serial.print("keyboardRate : ");
                Serial.print(1000.0 / (_rateTime1 - _rateTime0));
                Serial.println(" Hz");
            }
        #endif
    #endif
}