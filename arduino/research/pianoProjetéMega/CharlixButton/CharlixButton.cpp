#include "CharlixButton.h"
#include <Arduino.h>
#include <arduino.h>


CharlixButton::CharlixButton(const uint8_t pins [], uint8_t pinLen)
{
	_maxNote = 88;
	_initPins(pins, pinLen);
}

void CharlixButton::init(){
	if(!Serial)
	{
		Serial.begin(9600);
		while(!Serial);
		Serial.flush();
		Serial.println("SERIAL : OK");
	}
	_initButtons();
	_initNotes();
}

void CharlixButton::_initPins(const uint8_t pins [], uint8_t pinLen)
{
	_pinsLen = pinLen;
	_pins = (uint8_t*) &pins[0];
}

void CharlixButton::_initButtons()
{
	buttonLen = _pinsLen * (_pinsLen - 1);

	buttons = (BUTTON *)malloc(buttonLen * sizeof(BUTTON));
   
	if (buttons==NULL)
	{
		Serial.print("BUTTONS MEMORY ALLOC FAILED : ");
		Serial.println(buttonLen * sizeof(BUTTON));
		//		exit(1);
	}else{
		Serial.print("BUTTONS MEMORY ALLOCATED : ");
		Serial.println(buttonLen * sizeof(BUTTON));
		Serial.println(sizeof(BUTTON));
	}
	
	for (uint8_t k = 0; k < buttonLen; k++)
	{
		buttons[k] = BUTTON {
			k, false, false, _millis(), _millis()
		};
	}
	Serial.println("BUTTONS : OK");
}

void CharlixButton::_initNotes()
{
	noteLen = buttonLen / 2;
	if(noteLen > _maxNote)
	{
		noteLen = _maxNote;
	}
	notes = (NOTE *)malloc(noteLen * sizeof(NOTE));
	if (notes==NULL)
	{
		Serial.print("NOTES MEMORY ALLOC FAILED : ");
		Serial.println(noteLen * sizeof(NOTE));
		//		exit(1);
	}else{
		Serial.print("NOTES MEMORY ALLOCATED : ");
		Serial.println(noteLen * sizeof(NOTE));
		Serial.println(sizeof(NOTE));
	}
	
	BUTTON *btn_ptr = buttons;
	
	for (uint8_t k = 0; k < buttonLen; k++)
	{
		notes[k] = NOTE {
			k, false, false, btn_ptr++, btn_ptr++
		};
	}
	Serial.println("NOTES : OK");
}



void CharlixButton::update()
{
	#ifdef DEBUG_RATE
		#if DEBUG_RATE
			_rateTime0 = _millis();
		#endif
	#endif

	uint8_t btnCmp = 0 ;

	_pinOffset = 0 ;
	for (int i = 0; i < _pinsLen; i ++ )
	{
		uint8_t j = 0 ;
		for ( ; j < _pinsLen - 1; j ++ )
		{
			digitalWrite(_getPin(j), LOW);
		}
		digitalWrite( _getPin(j), HIGH);

		for (j = 0 ; j < _pinsLen - 1; j ++ )
		{
			pinMode(_getPin(j), INPUT);
		}
		for (j = 0 ; j < _pinsLen - 1; j ++ )
		{
			if(buttons[btnCmp].state != digitalRead(_getPin(j)))//buttons[btnCmp].stateChanged = (random(100) < 1 == 0);
			{
				buttons[btnCmp].stateChanged	= true;
				buttons[btnCmp].oldChangedTime  = buttons[btnCmp].changedTime;
				buttons[btnCmp].changedTime	 = _millis();
				buttons[btnCmp].state		   = !buttons[btnCmp].state;
			}
			else
			{
				buttons[btnCmp].stateChanged = false;
			}
			btnCmp++;
		}
		for (j = 0 ; j < _pinsLen - 1; j ++ )
		{
			pinMode(_getPin(j), OUTPUT);
		}
		_pinOffset++;
	}

	#ifdef DEBUG_RATE
		#if DEBUG_RATE
			_rateTime1 = _debugRateTime1 = _millis();
			if( _debugRateTime1 - _debugRateTime0 > DEBUG_RATE){
				_debugRateTime0 = _millis();
				Serial.print("keyboardRate : ");
				Serial.print(1000.0 / (_rateTime1 - _rateTime0));
				Serial.println(" Hz");
			}
		#endif
	#endif
}

void CharlixButton::process(){
	for (uint8_t k = 0; k < noteLen; k++)
	{
		if(notes[k].front->state && !notes[k].back->state){
			Serial.print("1");
			//  printf("1");
		}else{
			Serial.print("0");
			//  printf("0");
		}
		
		if(/* DISABLES CODE */ (false))
		{
			if(notes[k].front->stateChanged){
				if(! notes[k].front->state){
					//printf("note %i : ", notes[k].id);
					//printf("ON at velocity : %lu\n", notes[k].front->changedTime - notes[k].back->changedTime);
				}
			}
			if(notes[k].back->stateChanged){
				if(notes[k].back->state){
					//printf("note %i : ", notes[k].id);
					//printf("OFF at velocity : %lu\n", notes[k].back->changedTime - notes[k].front->changedTime);
				}
			}
		}
	}
	//printf("\n");
	Serial.print("\n");
}


uint8_t CharlixButton::_getPin(const uint8_t j){
	return _pins[ (j + _pinOffset) % _pinsLen ];
}

unsigned long CharlixButton::_millis()
{
	return millis() ;//std::chrono::duration_cast< std::chrono::milliseconds >(std::chrono::system_clock::now().time_since_epoch()).count();
}