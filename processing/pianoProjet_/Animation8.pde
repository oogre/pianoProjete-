/*
Exploding bubbles
*/

class Animation8 extends Anim implements Animation{
  color c;
  float speed;
  ArrayList<BounceParticle> particles = new ArrayList<BounceParticle>();

  public Animation8(){
    c = colors[int(random(colors.length))];
    particles.add(new BounceParticle(new PVector(random(width), height), new PVector(random(-1, 1), random(-1)), random(15,30), true));

  }

  void show(){
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

  void attack(){
    particles.get(0).vel.y = -this.getVelocity()/2.0;
    if(true){
      this.setStatus(Animation.SUSTAIN);
    }
  }

  void sustain() {
    if(true){
      this.setStatus(Animation.DECAY);
    }
  }

  void decay() {
    show();
    if(particles.size()<1)this.setStatus(Animation.DEAD);
  }

  class BounceParticle{
    PVector loc,vel;
    float r;
    color c;
    boolean moving;
    float life;
    float maxLife;
    BounceParticle(PVector loc, PVector vel, float r, boolean moving){
      this.loc = loc;
      this.vel = vel;
      this.r = r;
      this.moving = moving;
      vel.mult(3);

      c = colors[int(random(colors.length))];
      life = random(50,100);
      maxLife = life;
    }

    void draw(){
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

    void move(){
      loc.add(vel);
      if(moving)boundary();
    }

    void boundary(){
      if(loc.x<0 || loc.x>width){
        explode();
      }
      if(loc.y<0 || loc.y>height){
        explode();
      }
    }

    void explode(){
      for(int i=0;i<360;i+=6){
        float r = random(1,1.5);
        particles.add(new BounceParticle(new PVector(loc.x, loc.y), new PVector(sin(radians(i))*r, cos(radians(i))*r), random(2,7), false));
      }
      particles.remove(this);
    }
  }
}
