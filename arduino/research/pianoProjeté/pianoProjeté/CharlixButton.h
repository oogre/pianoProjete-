#define DEBUG_RATE 0

#ifndef CharlixButton_h
  #define CharlixButton_h

  typedef struct {
    int id ;
    bool state;
    bool stateChanged;
    unsigned long changedTime;
    unsigned long oldChangedTime;
  } BUTTON;

  typedef struct {
    int id ;
    bool state;
    bool stateChanged;
    BUTTON *front;
    BUTTON *back;
  } NOTE;

  class CharlixButton
  {
    public:
      CharlixButton(const int pins [], int pinLen);
      CharlixButton(const int baudrate, const int pins [], int pinLen);
      CharlixButton(const int baudrate, const int pins [], int pinLen, const int maxNote);
      void init();
      void update();
      void process();
      int buttonLen;
      BUTTON *buttons;
      NOTE *notes;
      int noteLen;
    private:
      void _initPins(const int pins [], int pinLen);
      void _initButtons();
      void _initNotes();
      unsigned long _millis();
      int *_pins;
      int _pinsLen;
      int _maxNote;
      int _baudrate;

    #ifdef DEBUG_RATE
      unsigned long _debugRateTime0;
      unsigned long _debugRateTime1;
      unsigned long _rateTime0;
      unsigned long _rateTime1;
    #endif
  };

#endif

   