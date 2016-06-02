#include "CharlixButton.h"
#include <chrono>
#include <iostream>

#define RAND_MAX 1


CharlixButton::CharlixButton(const int pins [], int pinLen)
{
    _maxNote = 88;
    _initPins(pins, pinLen);
    _initButtons();
    _initNotes();
}

void CharlixButton::_initPins(const int pins [], int pinLen)
{
	_pinsLen = pinLen;
    _pins = (int*) &pins[0];
}


void CharlixButton::_initButtons()
{
	buttonLen = _pinsLen * (_pinsLen - 1);

    buttons = (BUTTON *)calloc(buttonLen, sizeof(BUTTON));
    if (buttons==NULL) {
        exit(1);
    }
    
	for (int k = 0; k < buttonLen; k++)
	{
		buttons[k] = BUTTON {
            k, false, false, _millis(), _millis()
		};
	}
}

unsigned long CharlixButton::_millis()
{
    return std::chrono::duration_cast< std::chrono::milliseconds >(std::chrono::system_clock::now().time_since_epoch()).count();
}

void CharlixButton::_initNotes()
{
	noteLen = buttonLen / 2;
	if(noteLen > _maxNote){
		noteLen = _maxNote;
	}
    notes = (NOTE *)calloc(noteLen, sizeof(NOTE));
    if (notes==NULL) {
        exit(1);
    }
    
    BUTTON *btn_ptr = buttons;
    
    for (int k = 0; k < buttonLen; k++)
    {
        notes[k] = NOTE {
            k, false, false, btn_ptr++, btn_ptr++
        };
    }
}

void CharlixButton::update()
{
    #ifdef DEBUG_RATE
        #if DEBUG_RATE
            _rateTime0 = _millis();
        #endif
    #endif
	int btnCmp = 0 ;
	int pinOffset = 0 ;
	for (int i = 0; i < _pinsLen; i ++ )
	{
		int j = 0 ;
		for ( ; j < _pinsLen - 1; j ++ )
		{
			//digitalWrite(_pins[(j+pinOffset) % _pinsLen], LOW);
		}
		//digitalWrite(_pins[(j+pinOffset) % _pinsLen], HIGH);

		for (j = 0 ; j < _pinsLen - 1; j ++ )
		{
			//pinMode(_pins[j], INPUT);
		}
		for (j = 0 ; j < _pinsLen - 1; j ++ )
		{
            
            buttons[btnCmp].stateChanged = (rand() % 88 == 0);

			if(buttons[btnCmp].stateChanged)
			{
				buttons[btnCmp].state = !buttons[btnCmp].state;
                buttons[btnCmp].oldChangedTime = buttons[btnCmp].changedTime;
				buttons[btnCmp].changedTime = _millis();
			}
			else
			{
				buttons[btnCmp].stateChanged = false;
			}
			btnCmp++;
		}
		for (j = 0 ; j < _pinsLen - 1; j ++ )
		{
			//pinMode(_pins[(j+pinOffset) % _pinsLen], OUTPUT);
		}
		pinOffset++;
	}

	#ifdef DEBUG_RATE
		#if DEBUG_RATE
            _rateTime1 = _debugRateTime1 = _millis();
			if( _debugRateTime1 - _debugRateTime0 > DEBUG_RATE){
				_debugRateTime0 = _millis();
				//Serial.print("keyboardRate : ");
				//Serial.print(1000.0 / (_rateTime1 - _rateTime0));
				//Serial.println(" Hz");
			}
		#endif
	#endif
}

void CharlixButton::process(){
    for (int k = 0; k < noteLen; k++)
    {
        if(notes[k].front->state && !notes[k].back->state){
            printf("1");
        }else{
            printf(".");
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
    printf("\n");
}