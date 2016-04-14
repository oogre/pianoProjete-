#include <Arduino.h>
#include <arduino.h>


#define DEBUG_RATE 0
#define DEBUG_LISTENER 0
#define DEBUG_STATE 0
#define MIDI 1
#define MIDI_CHANNEL 1

#ifndef CharlixButton_h
	#define CharlixButton_h

	typedef struct {
        uint8_t id ;
        bool mapped;
        bool state;
		bool stateChanged;
		uint32_t changedTime;
		uint32_t oldChangedTime;
	} BUTTON;

	typedef struct {
		uint8_t id ;
        uint8_t midi ;
		bool state;
		bool stateChanged;
		BUTTON *front;
		BUTTON *back;
	} NOTE;

	class CharlixButton
	{
	public :
		CharlixButton(const uint8_t pins [], uint8_t pinLen);
		void init();
		void update();
		void process();
        void noteOn(uint8_t pitch, uint8_t velocity);
        void noteOff(uint8_t pitch, uint8_t velocity);
        
		BUTTON *buttons;
		uint8_t buttonLen;
		
		NOTE *notes;
		uint8_t noteLen;
	
        
	private:
		void _initPins(const uint8_t pins [], uint8_t pinLen);
		void _initButtons();
		void _initNotes();

        uint8_t _getVelocity(const uint32_t time);
		uint8_t _getPin(const uint8_t j);
		uint8_t _pinOffset;
		uint32_t _millis();
            
		uint8_t *_pins;
		uint8_t _pinsLen;
		uint8_t _maxNote;
		uint8_t _baudrate;

	#ifdef DEBUG_RATE
		#if DEBUG_RATE
			uint32_t _debugRateTime0;
			uint32_t _debugRateTime1;
			uint32_t _rateTime0;
			uint32_t _rateTime1;
		#endif
	#endif
	};
#endif