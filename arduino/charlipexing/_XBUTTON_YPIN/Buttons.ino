
void setupButtons ()
{
  for (byte i = 0; i < pinLen; i ++ )
  {
    pinMode(pins[i], OUTPUT);
  }

  byte buttonCmp = 0;
  for (byte i = 0; i < noteLen; i ++ )
  {
    notes[i] = {
      0, 0,
      {buttonCmp++, buttonCmp++},
      {true, true},
      {false, false}
    };
  }
}
void readButtons () {
  byte noteCmp = 0;
  byte buttonCmp = 0;
  for (byte i = 0; i < pinLen; i ++ )
  {
    byte j = 0 ;
    for ( ; j < pinLen - 1; j ++ )
    {
      digitalWrite(pins[j], LOW);
    }
    digitalWrite(pins[j], HIGH);


    j = 0;
    for (j = 0 ; j < pinLen - 1; j ++ )
    {
      pinMode(pins[j], INPUT);
    }
    for (j = 0 ; j < pinLen - 1; j ++ )
    {
      boolean state = digitalRead(pins[j]);
      notes[noteCmp].stateChanged[buttonCmp] = (state != notes[noteCmp].state[buttonCmp]);
      notes[noteCmp].state[buttonCmp] = state;
      buttonCmp++;
      if (buttonCmp >= 2) {
        buttonCmp = 0 ;
        noteCmp++ ;
      }
    }
    for (j = 0 ; j < pinLen - 1; j ++ )
    {
      pinMode(pins[j], OUTPUT);
    }

    byte tmpPins = pins[0];
    for (j = 1 ; j < pinLen; j ++ )
    {
      pins[j - 1] = pins[j];
    }
    pins[j - 1] = tmpPins;
  }
}
