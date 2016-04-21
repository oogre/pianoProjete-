void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(13, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:

  delay(random(2000));
  digitalWrite(13, HIGH);
  Serial.write(0x90);
  Serial.write(127);
  Serial.write(80);
  
  delay(random(2000));
  digitalWrite(13, LOW);
  Serial.write(0x80);
  Serial.write(127);
  Serial.write(80);
}
