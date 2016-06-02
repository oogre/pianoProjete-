import themidibus.*; //Import the library

static final String ROOT_PATH =  "/Users/ogre/Work/6102/pianoProjeté";
static final String PROXY_PATH = ROOT_PATH+"/openframeworks/proxy/serial2midi/bin";

static class MIDI {
  static MidiBus myBus;

  static public void init(PApplet parent) {
    myBus = new MidiBus(parent);
    myBus.addInput(0);
  }

  static public void startProxy() {
    String[] params = { PROXY_PATH+"/serial2midi.app" };
    launch(params);
  }
  static public void stopProxy() {
    String [] params = {PROXY_PATH+"/killSerial2Midi.sh"};
    exec(params);
  }
}

void noteOn(Note note) {
  try
  {
    animationSwitcher( animations2Notes.get(note.pitch), true, note.velocity());
  }
  catch (NullPointerException e) 
  {
    println("No Anim setup for note : "+ note.pitch);
  }
}

void noteOff(Note note) {
  try
  {
    animationSwitcher(animations2Notes.get(note.pitch), false, note.velocity());
  }
  catch (NullPointerException e)
  {
    println("No Anim setup for note : "+ note.pitch);
  }
}