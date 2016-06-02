class Animation1 extends Anim implements Animation {
  float alpha = 0 ;
  float alphaInc = 0 ;
  int opacity = 0;

  public Animation1() {
    this.alphaInc = random(1);
  }

  void attack(){
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

  void sustain() {
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

  void decay() {
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