#include "Arduino.h"
#ifndef CharliplexButton_h
#define CharliplexButton_h

  
  typedef struct BUTTON {
    int id ;
    boolean state;
    boolean stateChanged;
  };
  
  class CharliplexButton
  {
   public:
     CharliplexButton(int [] pins, int pinsLen);
     boolean [] buttons;
     int buttonsLen;
     void update();
   private:
     int [] _pins;
     int _pinsLen;
  };
  
#endif

