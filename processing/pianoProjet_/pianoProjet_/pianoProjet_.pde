import java.lang.reflect.*;
import java.lang.Class;
import java.lang.ClassLoader;
 
HashMap <Integer, Class> animations2Notes = new HashMap<Integer, Class>(); 

void setup() {
  size(800, 600);
  /* 
      // START MIDI LISTENER //
  
      MIDI.startProxy();
      MIDI.init(this);
  */
  
  /*
     Liaison entre le pitch d'une note jouée et une animation;
     MIDI : 81 > Pitch : A4 > frequence : 880.00hz > char : 'q'
     MIDI : 87 > Pitch : Eb5 > frequence : 1244.50hz > char : 'w'
  */
  animations2Notes.put(81, Animation0.class);
  animations2Notes.put(87, Animation1.class);
  animations2Notes.put(69, Animation2.class);
}

ArrayList <Animation> animations = new ArrayList<Animation>();

void draw() {
  println(Anim.GEN_INIT_COUNTER + " : " + Anim.LOCAL_INIT_COUNTER[0] + " : " + Anim.LOCAL_INIT_COUNTER[1] + " : " + Anim.LOCAL_INIT_COUNTER[2]);
  background(0);
  try {
    for (int i = animations.size() - 1; i >= 0; i--)
    {
      Animation anim = animations.get(i);
      if (anim != null) {
        switch(anim.getStatus()) {
        case Animation.DEAD:
          animations.remove(i);
          break;
        case Animation.ATTACK:
          anim.attack();
          break;
        case Animation.SUSTAIN:
          anim.sustain();
          break;
        case Animation.DECAY:
          anim.decay();
          break;
        }
      }
    }
  }
  catch(Exception e) {
    println(e);
  }
}

void keyPressed(){
  /*
    noteOn(new Note(int Channel, int pitch, int velocity));
    Ceci va déclancher l'attack de l'anim liée au keyCode
  */
  noteOn(new Note(1, keyCode, (int)random(2, 10)));
}

void keyReleased(){

  /*
    noteOff(new Note(int Channel, int pitch, int velocity));
    Ceci va déclancher le decay de l'anim liée au keyCode
  */
  noteOff(new Note(1, keyCode, (int)random(2, 10)));
}

void exit() {  
  /* 
      // STOP MIDI LISTENER //
  
      MIDI.stopProxy();
  */
  super.exit();
}