/*
182 theorique
176 used by keyboard
  6 left for paddles

80 Hz keyboard listenRate
*/  
#include <CharlixButton.h>
#define BAUDRATE 9600

byte pins [] = {8, 9, 10, 11, 12, 13, 7, 6, 5, 4, 3, 2, A0, A0} ;
CharlixButton cb(BAUDRATE, pins, 14);

void setup()
{
  cb.init();
}

void loop()
{
  cb.update();
}

