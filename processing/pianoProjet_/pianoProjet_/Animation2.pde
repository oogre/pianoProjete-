class Animation2 extends Anim implements Animation {
  float alpha = 0 ;
  float alphaInc = 0 ;
  int opacity = 0;

  public Animation2() {
    super(2);
    this.alphaInc = random(1);
  }

  void attack(){
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

  void sustain() {
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

  void decay() {
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