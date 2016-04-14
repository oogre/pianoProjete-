#include "CharlixButton.h"
#include <Arduino.h>
#include <arduino.h>


CharlixButton::CharlixButton(const uint8_t pins [], uint8_t pinLen)
{
	_maxNote = 88;
	_initPins(pins, pinLen);
}

void CharlixButton::init(){
    
    #if DEBUG_STATE
        Serial.begin(9600);
        while(!Serial);
        Serial.flush();
        Serial.println("MIDI SERIAL 9600 : OK");
    #endif
    
    #if MIDI
        Serial.begin(9600);
        while(!Serial1);
        Serial.flush();
        #if DEBUG_STATE
            Serial.println("MIDI SERIAL 9600 : OK");
        #endif
    #endif

    
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
        #if DEBUG_STATE
            Serial.print("BUTTONS MEMORY ALLOC FAILED : ");
            Serial.println(buttonLen * sizeof(BUTTON));
        #endif
		//		exit(1);
	}else{
        #if DEBUG_STATE
            Serial.print("BUTTONS MEMORY ALLOCATED : ");
            Serial.println(buttonLen * sizeof(BUTTON));
        #endif
    }
	
	for (uint8_t k = 0; k < buttonLen; k++)
	{
		buttons[k] = BUTTON {
			k, false, false, false, _millis(), _millis()
		};
	}
    update();
    
    
    #if DEBUG_LISTENER
        while (true) {
            update();
        }
    #endif
    
    #if DEBUG_STATE
        Serial.println("BUTTONS : OK");
    #endif
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
        #if DEBUG_STATE
            Serial.print("NOTES MEMORY ALLOC FAILED : ");
            Serial.println(noteLen * sizeof(NOTE));
        #endif
	}else{
        #if DEBUG_STATE
            Serial.print("NOTES MEMORY ALLOCATED : ");
            Serial.println(noteLen * sizeof(NOTE));
        #endif
	}
	
	BUTTON *btn_ptr = buttons;
    
    
    for (uint8_t k = 0; k < noteLen; k++)
    {
        notes[k] = NOTE {
            k, (_maxNote-k + 20), false, false, NULL, NULL
        };
    }
    
    uint8_t mappedNote = 0 ;
    
    #if DEBUG_STATE
        Serial.println("NOTES MAPPING PROCESS : ");
        Serial.println("PRESS EACH KEYS FROM LOW TO HIGH");
    #endif
    while(mappedNote < noteLen){
        update();
        for (uint8_t k = 0; k < buttonLen; k++)
        {
            if(buttons[k].stateChanged && !buttons[k].state && !buttons[k].mapped){
                notes[mappedNote].back = btn_ptr + k;
                notes[mappedNote].back->mapped = true;
                #if DEBUG_STATE
                    Serial.print("BUTTON : ");
                    Serial.print(notes[mappedNote].back->id);
                    Serial.print(" MAPPED AS BACK TO NOTE : ");
                    Serial.println(notes[mappedNote].id);
                #endif
            }
            if(buttons[k].stateChanged && buttons[k].state && !buttons[k].mapped){
                notes[mappedNote].front = btn_ptr + k;
                notes[mappedNote].front->mapped = true;
                #if DEBUG_STATE
                    Serial.print("BUTTON : ");
                    Serial.print(notes[mappedNote].front->id);
                    Serial.print(" MAPPED AS FRONT TO NOTE : ");
                    Serial.println(notes[mappedNote].id);
                
                    Serial.print("NOTE : ");
                    Serial.print(notes[mappedNote].id);
                    Serial.println(" MAPPED !");
                #endif
                mappedNote++;
            }
        }
    }
    #if DEBUG_STATE
        Serial.println("NOTES : OK");
    #endif
    
    
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
				buttons[btnCmp].changedTime     = _millis();
				buttons[btnCmp].state           = !buttons[btnCmp].state;
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
        
        #if DEBUG_LISTENER
            for (uint8_t k = 0; k < buttonLen; k++)
            {
                Serial.print(buttons[k].state);
            }
            Serial.println("");
        #endif
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
        if(/* ENABLES CODE */ (true))
        {
            if(notes[k].front->stateChanged && notes[k].front->state && !notes[k].back->state)
            {
                uint8_t velocity;
                if(notes[k].state){
                    velocity = _getVelocity(notes[k].front->changedTime - notes[k].front->oldChangedTime);
                }else{
                    velocity = _getVelocity(notes[k].front->changedTime - notes[k].back->changedTime);
                }
                notes[k].state = true;
                noteOn(notes[k].midi, velocity);
                #if DEBUG_STATE
                    Serial.print("NOTE : ");
                    Serial.print(notes[k].id);
                    Serial.print(" VELOCITY : ");
                    Serial.print(velocity);
                    Serial.println(" ON");
                #endif
            }

            if(notes[k].back->stateChanged && !notes[k].front->state && notes[k].back->state)
            {
                uint8_t velocity;
                if(!notes[k].state){
                    velocity = _getVelocity(notes[k].back->changedTime - notes[k].back->oldChangedTime);
                }else{
                    velocity = _getVelocity(notes[k].back->changedTime - notes[k].front->changedTime);
                }
                
                notes[k].state = false;
                noteOff(notes[k].midi, velocity);
                #if DEBUG_STATE
                    Serial.print("NOTE : ");
                    Serial.print(notes[k].id);
                    Serial.print(" VELOCITY : ");
                    Serial.print(velocity);
                    Serial.println(" OFF");
                #endif
            }
        }
    }
}

void CharlixButton::noteOn(uint8_t pitch, uint8_t velocity) {
    #if MIDI
        Serial.write(0x90 + MIDI_CHANNEL);
        Serial.write(pitch);
        Serial.write(velocity);
    #endif
}
void CharlixButton::noteOff(uint8_t pitch, uint8_t velocity) {
    #if MIDI
        Serial.write(0x80 + MIDI_CHANNEL);
        Serial.write(pitch);
        Serial.write(velocity);
    #endif
}

uint8_t CharlixButton::_getVelocity(const uint32_t time){
    return min(127, max(0, map(time, 0, 900, 10, 127)));
}
uint8_t CharlixButton::_getPin(const uint8_t j){
	return _pins[ (j + _pinOffset) % _pinsLen ];
}

unsigned long CharlixButton::_millis()
{
	return millis() ;//std::chrono::duration_cast< std::chrono::milliseconds >(std::chrono::system_clock::now().time_since_epoch()).count();
}