import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.lang.reflect.*; 
import java.lang.Class; 
import java.lang.ClassLoader; 
import themidibus.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class pianoProjet_ extends PApplet {




 
HashMap <Integer, Class> animations2Notes = new HashMap<Integer, Class>(); 

public void setup() {
  
  /* 
      // START MIDI LISTENER //
  
      MIDI.startProxy();
      MIDI.init(this);
  */
  
  /*
     Liaison entre le pitch d'une note jou\u00e9e et une animation;
     MIDI : 81 > Pitch : A4 > frequence : 880.00hz > char : 'q'
     MIDI : 87 > Pitch : Eb5 > frequence : 1244.50hz > char : 'w'
  */
  animations2Notes.put(81, Animation0.class);
  animations2Notes.put(87, Animation1.class);
  animations2Notes.put(69, Animation2.class);
}

ArrayList <Animation> animations = new ArrayList<Animation>();

public void draw() {
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

public void keyPressed(){
  /*
    noteOn(new Note(int Channel, int pitch, int velocity));
    Ceci va d\u00e9clancher l'attack de l'anim li\u00e9e au keyCode
  */
  noteOn(new Note(1, keyCode, (int)random(2, 10)));
}

public void keyReleased(){

  /*
    noteOff(new Note(int Channel, int pitch, int velocity));
    Ceci va d\u00e9clancher le decay de l'anim li\u00e9e au keyCode
  */
  noteOff(new Note(1, keyCode, (int)random(2, 10)));
}

public void exit() {  
  /* 
      // STOP MIDI LISTENER //
  
      MIDI.stopProxy();
  */
  super.exit();
}
interface Animation {
  public void attack();
  public void sustain();
  public void decay();
  public byte getStatus();
  public void setStatus(byte s);
  public int getVelocity();
  public int setVelocity(int v);
  
  public static final byte DEAD		= 0 ;
  public static final byte ATTACK	= 1 ;
  public static final byte SUSTAIN	= 2 ;
  public static final byte DECAY	= 3 ;

}

static class Anim implements Animation {
  private byte status = Animation.DEAD;
  private int velocity = 0;
  
  public static int GEN_INIT_COUNTER = 0;
  public static int [] LOCAL_INIT_COUNTER = new int [512];

  Anim(){
    Anim.GEN_INIT_COUNTER++;
  }
  Anim(int id){
    if(id < 0 || id >Anim.LOCAL_INIT_COUNTER.length){
      
    }
    Anim.LOCAL_INIT_COUNTER[id] ++;
    Anim.GEN_INIT_COUNTER++;
  }

  public void attack() {
  }

  public void sustain() {
  }

  public void decay() {
  }

  public byte getStatus() {
    return status;
  }
  public void setStatus(byte s) {
    if(s == Animation.ATTACK && status == Animation.DEAD){
      status = Animation.ATTACK;
    }
    if(s == Animation.SUSTAIN && status == Animation.ATTACK){
      status = Animation.SUSTAIN;
    }
    if(s == Animation.DECAY && status != Animation.DECAY){
      status = Animation.DECAY;
    }
    if(s == Animation.DEAD && status == Animation.DECAY){
      status = Animation.DEAD;
    }
  }

  public int getVelocity() {
    return velocity;
  }
  public int setVelocity(int v) {
    return velocity = v;
  }
}

public void animationSwitcher(Class c, boolean start, int velocity) {
  if (null == c) throw new NullPointerException("ANIM NOT SET"); 
  if (start)
  {
    try
    {

      Constructor constr = c.getConstructor(pianoProjet_.class);
      Animation object = (Animation) constr.newInstance(this);
      
      println(Anim.GEN_INIT_COUNTER);
      object.setStatus(Animation.ATTACK);
      object.setVelocity(velocity);
      animations.add(object);
    }
    catch (NoSuchMethodException e) 
    {
      println(e);
    }
    catch (Exception e) 
    {
      println(e);
    }
  } else
  {
    for (Animation anim : animations) {
      if ( c.isInstance(anim)) {
        anim.setVelocity(velocity);
        anim.setStatus(Animation.DECAY);
      }
    }
  }
}
class Animation0 extends Anim implements Animation 
{
  public Animation0() 
  {
    /*  
      this.getVelocity renvoie la valeur de la velocit\u00e9
      cette valeur est mise \u00e0 jour lorsque la note commence : "ATTACK"
           et elle est mise \u00e0 jour lorsque la note finit : "DECAY"

      La rapidit\u00e9 \u00e0 laquel la note passe 
      de DEAD \u00e0 SUSTAIN et de SUSTAIN \u00e0 DEAD 
      \u00e0 un rapport avec la valeur de velocit\u00e9.
             _________
            /|       |\
           / |       | \   
          /  |       |  \            
         /    SUSTAIN    \
        ATTACK       DECAY
    */
    super(0);
  }

  public void attack()
  {
    /*
      LE DEBUT DE L'ANIM
    */
    if (/* QUAND TU VEUX : FIN DE L'ATTACK */ true) {
      this.setStatus(Animation.SUSTAIN);
    }
  }

  public void sustain() {
    /*
      LE CORP DE L'ANIM
    */
  }

  public void decay() {
    /*
      LA MORT DE L'ANIM
    */
    if (/* QUAND TU VEUX : FIN DU DECAY */ true) {
      this.setStatus(Animation.DEAD);
    }
  }
}
class Animation1 extends Anim implements Animation {
  float alpha = 0 ;
  float alphaInc = 0 ;
  int opacity = 0;

  public Animation1() {
    super(1);
    this.alphaInc = random(1);
    
    
  }

  public void attack(){
    pushMatrix();
    translate(width / 2, height / 2);
    rotate(alpha+=alphaInc);
    translate(-width / 2, -height / 2);
    opacity = constrain(opacity, 0, 255);
    fill(255, 0, 0, opacity += getVelocity());
    noStroke();
    ellipseMode(CENTER);
    ellipse(width / 2, height / 2, 30, opacity);
    if (opacity >= 255) {
      this.setStatus(Animation.SUSTAIN);
    }
    popMatrix();
  }

  public void sustain() {
    pushMatrix();
    translate(width / 2, height / 2);
    rotate(alpha+=alphaInc);
    translate(-width / 2, -height / 2);
    fill(255, 0, 0, opacity);
    noStroke();
    ellipseMode(CENTER);
    ellipse(width / 2, height / 2, 30, opacity);
    popMatrix();
  }

  public void decay() {
    pushMatrix();
    translate(width / 2, height / 2);
    rotate(alpha+=alphaInc);
    translate(-width / 2, -height / 2);
    noStroke();
    ellipseMode(CENTER);
    opacity = constrain(opacity, 0, 255);
    fill(255, 0, 0, opacity -= getVelocity());
    ellipse(width / 2, height / 2, 30, opacity);
    if (opacity <= 0) {
      this.setStatus(Animation.DEAD);
    }
    popMatrix();
  }
}
class Animation2 extends Anim implements Animation {
  float alpha = 0 ;
  float alphaInc = 0 ;
  int opacity = 0;

  public Animation2() {
    super(2);
    this.alphaInc = random(1);
  }

  public void attack(){
    pushMatrix();
    translate(width / 2, height / 2);
    rotate(alpha+=alphaInc);
    translate(-width / 2, -height / 2);
    opacity = constrain(opacity, 0, 255);
    fill(255, 255);
    opacity += getVelocity();
    noStroke();
    rectMode(CENTER);
    rect(width / 2, height / 2, 100+opacity, 100+opacity);
    if (opacity >= 255) {
      this.setStatus(Animation.SUSTAIN);
    }
    popMatrix();
  }

  public void sustain() {
    pushMatrix();
    translate(width / 2, height / 2);
    rotate(alpha+=alphaInc);
    translate(-width / 2, -height / 2);
    fill(255, opacity);
    noStroke();
    rectMode(CENTER);
    rect(width / 2, height / 2, 100+opacity, 100+opacity);
    popMatrix();
  }

  public void decay() {
    pushMatrix();
    translate(width / 2, height / 2);
    rotate(alpha+=alphaInc);
    translate(-width / 2, -height / 2);
    noStroke();
    opacity = constrain(opacity, 0, 255);
    fill(255, 255);
    opacity -= getVelocity();
    rectMode(CENTER);
    rect(width / 2, height / 2, 100+opacity, 100+opacity);
    if (opacity <= 0) {
      this.setStatus(Animation.DEAD);
    }
    popMatrix();
  }
}
 //Import the library

static final String ROOT_PATH =  "/Users/ogre/Work/6102/pianoProjet\u00e9";
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

public void noteOn(Note note) {
  try
  {
    animationSwitcher( animations2Notes.get(note.pitch), true, note.velocity());
  }
  catch (NullPointerException e) 
  {
    println("No Anim setup for note : "+ note.pitch);
  }
}

public void noteOff(Note note) {
  try
  {
    animationSwitcher(animations2Notes.get(note.pitch), false, note.velocity());
  }
  catch (NullPointerException e)
  {
    println("No Anim setup for note : "+ note.pitch);
  }
}
  public void settings() {  size(800, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "pianoProjet_" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
