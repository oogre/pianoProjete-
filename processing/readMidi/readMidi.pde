import themidibus.*; //Import the library

MidiBus myBus;


final String PROXY = sketchPath()+"/../../openframeworks/proxy/serial2midi/bin/serial2midi.app";

void setup() {
  
  String[] params = {PROXY};
  launch(params);
  
  
  myBus = new MidiBus(this);
  myBus.addInput(0);
}
void draw() {
}

void noteOn(int channel, int pitch, int velocity, long timestamp, String bus_name) {
  println();
  println("Note On:");
  println("--------");
  println("Channel:"+channel);
  println("Pitch:"+pitch);
  println("Velocity:"+velocity);
  println("Recieved on Bus:"+bus_name);
}

void noteOff(int channel, int pitch, int velocity, long timestamp, String bus_name) {
  println();
  println("Note Off:");
  println("--------");
  println("Channel:"+channel);
  println("Pitch:"+pitch);
  println("Velocity:"+velocity);
  println("Recieved on Bus:"+bus_name);
}