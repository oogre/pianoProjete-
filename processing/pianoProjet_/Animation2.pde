class Animation2 extends Anim implements Animation {
  float alpha = 0 ;
  float alphaInc = 0 ;
  int opacity = 0;

  public Animation2() {
    this.alphaInc = random(1);
  }

  void attack(){
    pushMatrix();
    translate(width / 2, height / 2);
    rotate(alpha+=alphaInc);
    translate(-width / 2, -height / 2);
    opacity = constrain(opacity, 0, 255);
    stroke(0,0,1, 255);
    strokeWeight(alphaInc*10);
    noFill();
    opacity += getVelocity()/5.0;
    rectMode(CENTER);
    rect(width / 2, height / 2, 100+opacity, 100+opacity);
    if (opacity >= 255) {
      this.setStatus(Animation.SUSTAIN);
    }
    popMatrix();
  }

  void sustain() {
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

  void decay() {
    pushMatrix();
    translate(width / 2, height / 2);
    rotate(alpha+=alphaInc);
    translate(-width / 2, -height / 2);
    opacity = constrain(opacity, 0, 255);
    stroke(0,0,1, 255);
    strokeWeight(alphaInc*10);
    noFill();
    opacity -= getVelocity()/5.0;
    rectMode(CENTER);
    rect(width / 2, height / 2, 100+opacity, 100+opacity);
    if (opacity <= 0) {
      this.setStatus(Animation.DEAD);
    }
    popMatrix();
  }
}
