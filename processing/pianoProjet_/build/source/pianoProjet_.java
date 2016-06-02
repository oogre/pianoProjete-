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
  frameRate(120);
  //size(1280,720, P3D);
  
  colorMode(HSB, 1, 1, 1, 255);
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
  animations2Notes.put(73, Animation1.class);
  animations2Notes.put(69, Animation2.class);
  animations2Notes.put(82, Animation3.class);
  animations2Notes.put(84, Animation4.class);
  animations2Notes.put(89, Animation5.class);
  animations2Notes.put(85, Animation6.class);
  animations2Notes.put(81, Animation7.class);
  animations2Notes.put(87, Animation8.class);
  animations2Notes.put(79, Animation9.class);
}

ArrayList <Animation> animations = new ArrayList<Animation>();

public void draw() {
  surface.setTitle("fps:"+PApplet.parseInt(frameRate));
  //println("fps:"+int(frameRate));
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

public void keyPressed() {
  /*
    noteOn(new Note(int Channel, int pitch, int velocity));
   Ceci va d\u00e9clancher l'attack de l'anim li\u00e9e au keyCode
   */
  noteOn(new Note(1, keyCode, (int)random(1, 32)));
}

public void keyReleased() {
  /*
    noteOff(new Note(int Channel, int pitch, int velocity));
   Ceci va d\u00e9clancher le decay de l'anim li\u00e9e au keyCode
   */
  noteOff(new Note(1, keyCode, (int)random(1, 32)));
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


class Anim implements Animation {
  private byte status = Animation.DEAD;
  private int velocity = 0;

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
  }

  public void attack()
  {
    println(this.getVelocity());
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
    println(this.getVelocity());
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
    this.alphaInc = random(1);
  }

  public void attack(){
    pushMatrix();
    translate(width / 2, height / 2);
    rotate(alpha+=alphaInc);
    translate(-width / 2, -height / 2);
    opacity = constrain(opacity, 0, 255);
    fill(0, 0, 1, opacity += getVelocity());
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
    fill(0, 0, 1, opacity);
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
    fill(0,0,1, opacity -= getVelocity());
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
    this.alphaInc = random(1);
  }

  public void attack(){
    pushMatrix();
    translate(width / 2, height / 2);
    rotate(alpha+=alphaInc);
    translate(-width / 2, -height / 2);
    opacity = constrain(opacity, 0, 255);
    stroke(0,0,1, 255);
    strokeWeight(alphaInc*10);
    noFill();
    opacity += getVelocity()/5.0f;
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
    stroke(0,0,1, opacity);
    strokeWeight(alphaInc*10);
    noFill();
    rectMode(CENTER);
    rect(width / 2, height / 2, 100+opacity, 100+opacity);
    popMatrix();
  }

  public void decay() {
    pushMatrix();
    translate(width / 2, height / 2);
    rotate(alpha+=alphaInc);
    translate(-width / 2, -height / 2);
    opacity = constrain(opacity, 0, 255);
    stroke(0,0,1, 255);
    strokeWeight(alphaInc*10);
    noFill();
    opacity -= getVelocity()/5.0f;
    rectMode(CENTER);
    rect(width / 2, height / 2, 100+opacity, 100+opacity);
    if (opacity <= 0) {
      this.setStatus(Animation.DEAD);
    }
    popMatrix();
  }
}
class Animation3 extends Anim implements Animation {
  PVector[] rectPos;
  float step;
  int rectIndex;
  float alpha = 0;
  int gridSize;
  float w, h;
  boolean filled = false;
  boolean max = false;
  float speed;
  int c;

  public Animation3() {
    c = colors[PApplet.parseInt(random(colors.length))];
    filled = (random(1)<.5f)?true:false;
    gridSize = PApplet.parseInt(random(1, 15));
    w = PApplet.parseFloat(width)/PApplet.parseFloat(gridSize);
    h = PApplet.parseFloat(height)/PApplet.parseFloat(gridSize);
    step = w/PApplet.parseInt(random(5, 30));
    int rectPosLength = 0;
    for (int y=0; y<height; y+=h) {
      for (int x=0; x<width; x+=w) {
        rectPosLength++;
      }
    }
    rectPos = new PVector[rectPosLength];
    int index=0;
    for (int y=0; y<height; y+=h) {
      for (int x=0; x<width; x+=w) {
        rectPos[index]=new PVector(x, y); 
        index++;
      }
    }
    rectIndex = PApplet.parseInt(random(rectPos.length));
  }

  public void show() {
    pushMatrix();
    PVector pos = rectPos[rectIndex];
    translate(pos.x, pos.y);
    if (filled) {
      noStroke();
      fill(c, alpha);
      rect(0, 0, w, h);
    } else {
      noFill();
      strokeWeight(2.5f);
      stroke(c, alpha);
      for (float y=0; y<=h; y+=step) {
        for (float x=0; x<=w; x+=step) {
          point(x, y);
        }
      }
    }
    popMatrix();
  }

  public void attack() {
    speed=getVelocity();
      this.setStatus(Animation.SUSTAIN);
  }

  public void sustain() {
    //rectIndex = int(random(rectPos.length));
    //show();
    this.setStatus(Animation.DECAY);
  }

  public void decay() {
    if (!max) {
      if (alpha<=255)alpha+=speed;
      else max = true;
    }else{
      if(alpha >= 0)alpha-=speed;
    }
    show();
    if (alpha <= 0) {
      this.setStatus(Animation.DEAD);
    }
  }
}
class Animation4 extends Anim implements Animation {
  int segments = 200;
  float lineLength = width*1.5f;
  float step = lineLength/segments;
  int start, stop;
  float rot;
  float speed;
  float acc = 0;
  public Animation4() {
    rot = random(TWO_PI);
  }

  public void show() {
    pushMatrix();
    translate(width/2, height/2);
    rotateZ(rot);
    translate(-lineLength/2, 0);
    noFill();
    stroke(0,0,1);
    strokeWeight(2);
    beginShape();
    for (float x=0; x<lineLength; x+=step) {
      if (x<start&&x>stop)vertex(x, 0);
    }
    endShape(); 
    popMatrix();
  }

  public void attack() {
    acc = this.getVelocity()/5.0f;
    this.setStatus(Animation.SUSTAIN);
  }

  public void sustain() {
    this.setStatus(Animation.DECAY);
  }

  public void decay() {
    speed+=acc;
    show();
    if (start<lineLength)start+=speed;
    else {
      if (stop<lineLength)stop+=speed;
      else this.setStatus(Animation.DEAD);
    }
  }
}
class Animation5 extends Anim implements Animation {
  int pts = 80;
  float angle = 0;
  float radius = 40.0f;
  int segments = 200;
  float latheAngle = 0;
  float latheRadius = 100.0f;
  PVector vertices[], vertices2[];
  boolean isWireFrame = false;
  float helixOffset = 2.5f;
  float start, stop;
  float speed;
  float acc;
  float rspeed1;
  float rspeed2;
  public Animation5() {
    rspeed1 = random(30.0f, 50.0f);
    rspeed2 = random(2.0f, 5.0f);

    radius = random(40, 80);
    latheRadius = random(60, 130);
    segments = PApplet.parseInt(random(10, 100));
    pts = PApplet.parseInt(random(60, 120));
    isWireFrame = (random(1)>.5f)?true:false;
  }

  public void show() {
    lights();
    vertices = new PVector[pts+1];
    vertices2 = new PVector[pts+1];
    for (int i=0; i<=pts; i++) {
      vertices[i] = new PVector();
      vertices2[i] = new PVector();
      vertices[i].x = latheRadius + sin(radians(angle))*radius;
      vertices[i].z = cos(radians(angle))*radius-(helixOffset*segments)/2;
      angle+=360.0f/pts;
    }

    latheAngle = 0;
    pushMatrix();
    translate(width/2, height/2, 100);
    rotateX(HALF_PI);
    rotateY(frameCount/rspeed1*PI/160);
    rotateZ(frameCount/rspeed2*PI/90);
    for (int i=0; i<=segments; i++) {
      beginShape(QUAD_STRIP);
      //float h = map(i, 0, segments, 0, 1);
      int c = (i%2==0)?1:0;
      for (int j=0; j<=pts; j++) {
        if (isWireFrame) {
          strokeWeight(1.5f);
          stroke(0, 0, 1);
          noFill();
        } else {
          noStroke();
          fill(0, 0, c);
        }
        if (i<=start&&i>stop)vertex(vertices2[j].x, vertices2[j].y, vertices2[j].z);
        vertices2[j].x = cos(radians(latheAngle))*vertices[j].x;
        vertices2[j].y = sin(radians(latheAngle))*vertices[j].x;
        vertices2[j].z = vertices[j].z;
        vertices[j].z+=helixOffset;
        if (i<=start&&i>stop)vertex(vertices2[j].x, vertices2[j].y, vertices2[j].z);
      }
      latheAngle+=720.0f/segments;
      endShape();
    }
    popMatrix();
  }

  public void attack() {
    //show();
    speed = this.getVelocity()/10.0f;
    this.setStatus(Animation.SUSTAIN);
  }

  public void sustain() {
    //println("sustain");
    //show();
    this.setStatus(Animation.DECAY);
  }

  public void decay() {
    show();
    //println(stop);
    if (start<=segments)start+=speed;
    else {
      if (stop<=segments)stop+=this.getVelocity()/10.0f;
      else this.setStatus(Animation.DEAD);
    }
  }
}
class Animation6 extends Anim implements Animation {
  int pts = 80;
  float angle = 0;
  float radius = 40.0f;
  int segments = 200;
  float latheAngle = 0;
  float latheRadius = 100.0f;
  PVector vertices[], vertices2[];
  boolean isWireFrame = false;
  float start, stop;
  float speed;
  float acc;
  float rspeed1;
  float rspeed2;
  public Animation6() {
    rspeed1 = random(20.0f, 40.0f);
    rspeed2 = random(2.0f, 5.0f);

    radius = random(10, 50);
    latheRadius = random(100, 230);
    segments = PApplet.parseInt(random(20, 100));
    pts = PApplet.parseInt(random(20, 100));
    isWireFrame = (random(1)>.5f)?true:false;
  }

  public void show() {
    lights();
    vertices = new PVector[pts+1];
    vertices2 = new PVector[pts+1];
    for (int i=0; i<=pts; i++) {
      vertices[i] = new PVector();
      vertices2[i] = new PVector();
      vertices[i].x = latheRadius + sin(radians(angle))*radius;
      vertices[i].z = cos(radians(angle))*radius;
      angle+=360.0f/pts;
    }

    latheAngle = 0;
    pushMatrix();
    translate(width/2, height/2, 100);
    //rotateX(HALF_PI);
    rotateY(frameCount/rspeed1*PI/160);
    rotateZ(frameCount/rspeed2*PI/90);
    for (int i=0; i<=segments; i++) {
      beginShape(QUAD_STRIP);
      float h = map(i, 0, segments, 0, 1);
      for (int j=0; j<=pts; j++) {
        if (isWireFrame) {
          strokeWeight(1.5f);
          stroke(0, 0, 1);
          noFill();
        } else {
          noStroke();
          fill(h, 1, 1);
        }
        if (i<=start&&i>stop)vertex(vertices2[j].x, vertices2[j].y, vertices2[j].z);
        vertices2[j].x = cos(radians(latheAngle))*vertices[j].x;
        vertices2[j].y = sin(radians(latheAngle))*vertices[j].x;
        vertices2[j].z = vertices[j].z;
        if (i<=start&&i>stop)vertex(vertices2[j].x, vertices2[j].y, vertices2[j].z);
      }
      latheAngle+=360.0f/segments;
      endShape();
    }
    popMatrix();
  }

  public void attack() {
    //show();
    speed = this.getVelocity()/10.0f;
    this.setStatus(Animation.SUSTAIN);
  }

  public void sustain() {
    //println("sustain");
    //show();
    this.setStatus(Animation.DECAY);
  }

  public void decay() {
    show();
    //println(stop);
    if (start<=segments)start+=speed;
    else {
      if (stop<=segments)stop+=this.getVelocity()/10.0f;
      else this.setStatus(Animation.DEAD);
    }
  }
}
class Animation7 extends Anim implements Animation {
  float inc;
  float rspeed;
  float dspeed;
  float dd;
  int c1,c2;

  public Animation7()
  {
    c1 = colors[PApplet.parseInt(random(colors.length))];
    c2 = colors[PApplet.parseInt(random(colors.length))];
  }

  public void show(){
    pushMatrix();
    translate(width/2, height/2);
    strokeWeight(1);
    noFill();
    for (int x = -30; x < 30; x++) {
      for (int y = -23; y < 25; y++) {
        if (dist(x, y, 0, 0) < dd) {
          pushMatrix();
          translate(x*25, y*25);
          //color c = lerpColor(c1,c2,sin(radians(x*y+inc)));
          stroke(0xffffffff,sin(radians(x*y+inc))*125);
          rotate(radians(sin(radians(x*y+inc))*180));
          line(-10, 0, 10, 0);
          popMatrix();
      }
    }
  }
    popMatrix();
  }

  public void attack(){
    show();
    rspeed = this.getVelocity()/5.0f;
    dspeed = this.getVelocity()/2.0f;
    inc += rspeed;
    if(dd<30)dd+=dspeed;
    else {
      this.setStatus(Animation.SUSTAIN);
    }
  }

  public void sustain() {
    show();
    inc += rspeed;
  }

  public void decay() {
    show();
    rspeed = this.getVelocity()/5.0f;
    dspeed = this.getVelocity()/5.0f;
    inc += rspeed;
    if(dd>0)dd-=dspeed;
    else{
      this.setStatus(Animation.DEAD);
    }
  }
}
/*
Exploding bubbles
*/

class Animation8 extends Anim implements Animation{
  int c;
  float speed;
  ArrayList<BounceParticle> particles = new ArrayList<BounceParticle>();

  public Animation8(){
    c = colors[PApplet.parseInt(random(colors.length))];
    particles.add(new BounceParticle(new PVector(random(width), height), new PVector(random(-1, 1), random(-1)), random(15,30), true));

  }

  public void show(){
    pushMatrix();
    for (int i = 0; i < particles.size (); i++) {
      BounceParticle p = particles.get(i);
      p.draw();
      p.move();
      if (!p.moving) {
        if (p.life < 0) particles.remove(p);
        if (p.loc.x < -10 || p.loc.x > width+10) particles.remove(p);
        if (p.loc.y < -10 || p.loc.y > height+10) particles.remove(p);
      }
    }
    popMatrix();
  }

  public void attack(){
    particles.get(0).vel.y = -this.getVelocity()/2.0f;
    if(true){
      this.setStatus(Animation.SUSTAIN);
    }
  }

  public void sustain() {
    if(true){
      this.setStatus(Animation.DECAY);
    }
  }

  public void decay() {
    show();
    if(particles.size()<1)this.setStatus(Animation.DEAD);
  }

  class BounceParticle{
    PVector loc,vel;
    float r;
    int c;
    boolean moving;
    float life;
    float maxLife;
    BounceParticle(PVector loc, PVector vel, float r, boolean moving){
      this.loc = loc;
      this.vel = vel;
      this.r = r;
      this.moving = moving;
      vel.mult(3);

      c = colors[PApplet.parseInt(random(colors.length))];
      life = random(50,100);
      maxLife = life;
    }

    public void draw(){
      if(moving){
        noStroke();
        fill(c);
        ellipse(loc.x,loc.y,r,r);
      }else{
        life--;
        for(int i=0;i<r;i++){
          noStroke();
          fill(c,map(life,0,maxLife,0,255));
          ellipse(loc.x,loc.y,i,i);
        }
      }
    }

    public void move(){
      loc.add(vel);
      if(moving)boundary();
    }

    public void boundary(){
      if(loc.x<0 || loc.x>width){
        explode();
      }
      if(loc.y<0 || loc.y>height){
        explode();
      }
    }

    public void explode(){
      for(int i=0;i<360;i+=6){
        float r = random(1,1.5f);
        particles.add(new BounceParticle(new PVector(loc.x, loc.y), new PVector(sin(radians(i))*r, cos(radians(i))*r), random(2,7), false));
      }
      particles.remove(this);
    }
  }
}
class Animation9 extends Anim implements Animation {
  int c;
  ArrayList<Particle> particles = new ArrayList<Particle>();
  public Animation9(){
    for(int i=0;i<360;i+=15){
      float rand = random(1, 1.5f);
      int t = PApplet.parseInt(random(5));
      particles.add(new Particle(new PVector(width/2+sin(radians(i))*30, height/2+cos(radians(i))*30), new PVector(sin(radians(i)), cos(radians(i))), random(10, 30),t));
    }
  }

  public void show(){
    pushMatrix();
    for (int i = 0; i < particles.size (); i++) {
      Particle p = particles.get(i);
      p.draw();
      p.move();
      if (p.life < 0) particles.remove(p);
    }
    popMatrix();
  }

  public void attack(){
    //show();
    for(Particle p:particles)p.vel.mult(getVelocity()/5.0f);
    if(true){
      this.setStatus(Animation.SUSTAIN);
    }
  }

  public void sustain() {
    //show();
    if(true){
      this.setStatus(Animation.DECAY);
    }
  }

  public void decay() {
    show();
    if(particles.size()<1)this.setStatus(Animation.DEAD);
  }

  class Particle{
    PVector loc,vel;
    float r;
    int c;
    boolean moving;
    float life;
    float maxLife;
    int type;
    PVector[] history = new PVector[30];
    Particle(PVector loc, PVector vel, float r,int type){
      this.loc = loc;
      this.vel = vel;
      this.r = r;
      this.type = type;

      c = colors[PApplet.parseInt(random(colors.length))];
      life = random(50,200);
      maxLife = life;
      for(int i=0;i<history.length;i++)history[i]=loc.get();
    }

    public void draw(){
        life--;
          switch(type){
            case 0:
              noStroke();
              fill(c,map(life,0,maxLife,0,255));
              ellipse(loc.x,loc.y,r,r);
              break;
            case 1:
              noFill();
              strokeWeight(life/50.0f);
              stroke(c,map(life,0,maxLife,0,255));
              ellipse(loc.x,loc.y,r,r);
              break;
            case 2:
              noFill();
              strokeWeight(life/50.0f);
              stroke(c,map(life,0,maxLife,0,255));
              pushMatrix();
              translate(loc.x,loc.y);
              rotate(frameCount/100.0f);
              rect(0,0,r,r);
              popMatrix();
              break;
            case 3:
              noFill();
              strokeWeight(life/50.0f);
              stroke(c,map(life,0,maxLife,0,255));
              beginShape();
              for(int i=0;i<history.length;i++){
                vertex(history[i].x,history[i].y);
              }
              endShape();
              break;
            case 4:
              float alpha = map(life,0,maxLife,0,255);
              noStroke();
              for(int i=0;i<history.length;i++){
                fill(c,alpha-(255-i*8));
                ellipse(history[i].x,history[i].y,r,r);
              }
              break;
          }
    }

    public void move(){
      loc.add(vel);
      boundary();
      for(int i=0;i<history.length-1;i++){
        history[i]=history[i+1];
      }
      history[history.length-1] = loc.get();
    }

    public void boundary(){
      if(loc.x<0 || loc.x>width) vel.x *= -1;
      if(loc.y<0 || loc.y>height) vel.y *= -1;
    }
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
int[] colors = {
  0xffFFFFFF,
  0xffF8A31F,
  0xffD5FD84,
  0xff99FF5D,
  0xff9DFFF9,
  0xff83D9E0,
  0xff47AEB7,
  0xff213BA5,
  0xff63157A,
  0xffE678FA,
  0xffD71356,
  0xffEF2158,
  0xffFD5572
};
  public void settings() {  fullScreen(P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "pianoProjet_" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
