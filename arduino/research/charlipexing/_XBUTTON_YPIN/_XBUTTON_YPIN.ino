const byte DEBUG = false;
const byte pinLen = 3;
byte pins [] = {8, 9, 10 }; //, 11, 12, 13, 7, 6, 5, 4, 3, 2} ;

const byte buttonLen = pinLen * (pinLen - 1);
const byte noteLen = buttonLen / 2;

typedef struct NOTE {
  unsigned long time0;
  unsigned long time1;
  byte btn [2] ;
  boolean state [2] ;
  boolean stateChanged [2] ;
};

NOTE notes [noteLen];

void setup()
{
  setupButtons();
  Serial.begin(9600);
}

void loop()
{
  readButtons();
  
  for (byte i = 0; i < noteLen; i ++ )
  {
    if(notes[i].stateChanged[0])
    {
      if(notes[i].state[0]){
        notes[i].time0 = millis();
        Serial.print("NOTE ");
        Serial.print(i);
        Serial.print(" BUTTON 0 : ON DURATION: ");
      }
      else{
        notes[i].time0 = millis();
        Serial.print("NOTE ");
        Serial.print(i);
        Serial.print(" BUTTON 0 : OFF DURATION: ");;
      }
      Serial.println(notes[i].time0 - notes[i].time1);
    }
    if(notes[i].stateChanged[1])
    {
      if(notes[i].state[1]){
        notes[i].time1 = millis();
        Serial.print("NOTE ");
        Serial.print(i);
        Serial.print(" BUTTON 1 : ON DURATION: ");
      }
      else{
        notes[i].time1 = millis();
        Serial.print("NOTE ");
        Serial.print(i);
        Serial.print(" BUTTON 1 : OFF DURATION: ");
      }
      Serial.println(notes[i].time1 - notes[i].time0);
    }
  }
}
