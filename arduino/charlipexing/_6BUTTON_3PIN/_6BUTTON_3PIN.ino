
const byte len = 6;
boolean buttons [len];
const byte pinLen = 3;
byte pins [] = {8, 9, 10} ;

void setup() {
  // put your setup code here, to run once:
  pinMode(10, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(8, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  delay(5);
  digitalWrite(8, LOW);
  digitalWrite(9, LOW);
  digitalWrite(10, HIGH);
  pinMode(8, INPUT);
  pinMode(9, INPUT);
  buttons[0] = digitalRead(8);
  buttons[1] = digitalRead(9);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);


  delay(5);
  digitalWrite(10, LOW);
  digitalWrite(8, LOW);
  digitalWrite(9, HIGH);
  pinMode(10, INPUT);
  pinMode(8, INPUT);
  buttons[2] = digitalRead(10);
  buttons[3] = digitalRead(8);
  pinMode(10, OUTPUT);
  pinMode(8, OUTPUT);

  delay(5);
  digitalWrite(9, LOW);
  digitalWrite(10, LOW);
  digitalWrite(8, HIGH);
  pinMode(9, INPUT);
  pinMode(10, INPUT);
  buttons[4] = digitalRead(9);
  buttons[5] = digitalRead(10);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);

  for (byte i = 0; i < len; i ++ ) {
    Serial.print(buttons[i]);
  }
  Serial.println();
}
