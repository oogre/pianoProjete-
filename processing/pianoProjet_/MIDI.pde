import themidibus.*; //Import the library

static final String PROXY_PATH = ROOT_PATH+"/openframeworks/proxy/serial2midi/bin";

static class MIDI{
  static MidiBus myBus;
  static public void init(PApplet parent){
    myBus = new MidiBus(parent);
    myBus.addInput(0);
  }
  
  static public void startProxy() {
    String[] params = { PROXY_PATH+"/serial2midi.app" };
    launch(params);
  }
  static public void stopProxy(){
    String [] params = {PROXY_PATH+"/killSerial2Midi.sh"};
    exec(params);
  }
}
/*
void noteOn(Note note) {
  println();
  println("Note On:");
  println("--------");
  println("Channel:"+note.channel());
  println("Pitch:"+note.pitch());
  println("Velocity:"+note.velocity());
  println("Recieved on Bus:"+note.bus_name);
}

void noteOff(Note note) {
  println();
  println("Note Off:");
  println("--------");
   println("Channel:"+note.channel());
  println("Pitch:"+note.pitch());
  println("Velocity:"+note.velocity());
  println("Recieved on Bus:"+note.bus_name);
}

*/