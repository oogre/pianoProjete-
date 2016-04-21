static final String ROOT_PATH = "/Users/ogre/Work/6102/pianoProjeté";

void setup() {
  MIDI.startProxy();
  MIDI.init(this);
}

void draw() {
}


void exit() {
  MIDI.stopProxy();
  super.exit();
}