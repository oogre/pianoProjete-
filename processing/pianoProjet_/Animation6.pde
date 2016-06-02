class Animation6 extends Anim implements Animation {
  int pts = 80;
  float angle = 0;
  float radius = 40.0;
  int segments = 200;
  float latheAngle = 0;
  float latheRadius = 100.0;
  PVector vertices[], vertices2[];
  boolean isWireFrame = false;
  float start, stop;
  float speed;
  float acc;
  float rspeed1;
  float rspeed2;
  public Animation6() {
    rspeed1 = random(20.0, 40.0);
    rspeed2 = random(2.0, 5.0);

    radius = random(10, 50);
    latheRadius = random(100, 230);
    segments = int(random(20, 100));
    pts = int(random(20, 100));
    isWireFrame = (random(1)>.5)?true:false;
  }

  void show() {
    lights();
    vertices = new PVector[pts+1];
    vertices2 = new PVector[pts+1];
    for (int i=0; i<=pts; i++) {
      vertices[i] = new PVector();
      vertices2[i] = new PVector();
      vertices[i].x = latheRadius + sin(radians(angle))*radius;
      vertices[i].z = cos(radians(angle))*radius;
      angle+=360.0/pts;
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
          strokeWeight(1.5);
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
      latheAngle+=360.0/segments;
      endShape();
    }
    popMatrix();
  }

  void attack() {
    //show();
    speed = this.getVelocity()/10.0;
    this.setStatus(Animation.SUSTAIN);
  }

  void sustain() {
    //println("sustain");
    //show();
    this.setStatus(Animation.DECAY);
  }

  void decay() {
    show();
    //println(stop);
    if (start<=segments)start+=speed;
    else {
      if (stop<=segments)stop+=this.getVelocity()/10.0;
      else this.setStatus(Animation.DEAD);
    }
  }
}
