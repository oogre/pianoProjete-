class Animation9 extends Anim implements Animation {
  color c;
  ArrayList<Particle> particles = new ArrayList<Particle>();
  public Animation9(){
    for(int i=0;i<360;i+=15){
      float rand = random(1, 1.5);
      int t = int(random(5));
      particles.add(new Particle(new PVector(width/2+sin(radians(i))*30, height/2+cos(radians(i))*30), new PVector(sin(radians(i)), cos(radians(i))), random(10, 30),t));
    }
  }

  void show(){
    pushMatrix();
    for (int i = 0; i < particles.size (); i++) {
      Particle p = particles.get(i);
      p.draw();
      p.move();
      if (p.life < 0) particles.remove(p);
    }
    popMatrix();
  }

  void attack(){
    //show();
    for(Particle p:particles)p.vel.mult(getVelocity()/5.0);
    if(true){
      this.setStatus(Animation.SUSTAIN);
    }
  }

  void sustain() {
    //show();
    if(true){
      this.setStatus(Animation.DECAY);
    }
  }

  void decay() {
    show();
    if(particles.size()<1)this.setStatus(Animation.DEAD);
  }

  class Particle{
    PVector loc,vel;
    float r;
    color c;
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

      c = colors[int(random(colors.length))];
      life = random(50,200);
      maxLife = life;
      for(int i=0;i<history.length;i++)history[i]=loc.get();
    }

    void draw(){
        life--;
          switch(type){
            case 0:
              noStroke();
              fill(c,map(life,0,maxLife,0,255));
              ellipse(loc.x,loc.y,r,r);
              break;
            case 1:
              noFill();
              strokeWeight(life/50.0);
              stroke(c,map(life,0,maxLife,0,255));
              ellipse(loc.x,loc.y,r,r);
              break;
            case 2:
              noFill();
              strokeWeight(life/50.0);
              stroke(c,map(life,0,maxLife,0,255));
              pushMatrix();
              translate(loc.x,loc.y);
              rotate(frameCount/100.0);
              rect(0,0,r,r);
              popMatrix();
              break;
            case 3:
              noFill();
              strokeWeight(life/50.0);
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

    void move(){
      loc.add(vel);
      boundary();
      for(int i=0;i<history.length-1;i++){
        history[i]=history[i+1];
      }
      history[history.length-1] = loc.get();
    }

    void boundary(){
      if(loc.x<0 || loc.x>width) vel.x *= -1;
      if(loc.y<0 || loc.y>height) vel.y *= -1;
    }
  }
}
