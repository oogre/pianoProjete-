



boolean b01 = HIGH;
boolean b02 = HIGH;
boolean b03 = HIGH;
boolean b04 = HIGH;
boolean b05 = HIGH;
boolean b06 = HIGH;
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
  b01 = digitalRead(8);
  b02 = digitalRead(9);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);


  delay(5);
  digitalWrite(10, LOW);
  digitalWrite(8, LOW);
  digitalWrite(9, HIGH);
  pinMode(10, INPUT);
  pinMode(8, INPUT);
  b03 = digitalRead(10);
  b04 = digitalRead(8);
  pinMode(10, OUTPUT);
  pinMode(8, OUTPUT);
  
  delay(5);
  digitalWrite(9, LOW);
  digitalWrite(10, LOW);
  digitalWrite(8, HIGH);
  pinMode(9, INPUT);
  pinMode(10, INPUT);
  b05 = digitalRead(9);
  b06 = digitalRead(10);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);

  Serial.print(b01);
  Serial.print(" ");
  Serial.print(b02);
  Serial.print(" ");
  Serial.print(b03);
  Serial.print(" ");
  Serial.print(b04);
  Serial.print(" ");
  Serial.print(b05);
  Serial.print(" ");
  Serial.println(b06);
}
