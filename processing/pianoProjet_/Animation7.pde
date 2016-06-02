class Animation7 extends Anim implements Animation {
  float inc;
  float rspeed;
  float dspeed;
  float dd;
  color c1,c2;

  public Animation7()
  {
    c1 = colors[int(random(colors.length))];
    c2 = colors[int(random(colors.length))];
  }

  void show(){
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
          stroke(#ffffff,sin(radians(x*y+inc))*125);
          rotate(radians(sin(radians(x*y+inc))*180));
          line(-10, 0, 10, 0);
          popMatrix();
      }
    }
  }
    popMatrix();
  }

  void attack(){
    show();
    rspeed = this.getVelocity()/5.0;
    dspeed = this.getVelocity()/2.0;
    inc += rspeed;
    if(dd<30)dd+=dspeed;
    else {
      this.setStatus(Animation.SUSTAIN);
    }
  }

  void sustain() {
    show();
    inc += rspeed;
  }

  void decay() {
    show();
    rspeed = this.getVelocity()/5.0;
    dspeed = this.getVelocity()/5.0;
    inc += rspeed;
    if(dd>0)dd-=dspeed;
    else{
      this.setStatus(Animation.DEAD);
    }
  }
}
