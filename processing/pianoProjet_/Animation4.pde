class Animation4 extends Anim implements Animation {
  int segments = 200;
  float lineLength = width*1.5;
  float step = lineLength/segments;
  int start, stop;
  float rot;
  float speed;
  float acc = 0;
  public Animation4() {
    rot = random(TWO_PI);
  }

  void show() {
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

  void attack() {
    acc = this.getVelocity()/5.0;
    this.setStatus(Animation.SUSTAIN);
  }

  void sustain() {
    this.setStatus(Animation.DECAY);
  }

  void decay() {
    speed+=acc;
    show();
    if (start<lineLength)start+=speed;
    else {
      if (stop<lineLength)stop+=speed;
      else this.setStatus(Animation.DEAD);
    }
  }
}