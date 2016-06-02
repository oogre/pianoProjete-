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
  color c;

  public Animation3() {
    c = colors[int(random(colors.length))];
    filled = (random(1)<.5)?true:false;
    gridSize = int(random(1, 15));
    w = float(width)/float(gridSize);
    h = float(height)/float(gridSize);
    step = w/int(random(5, 30));
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
    rectIndex = int(random(rectPos.length));
  }

  void show() {
    pushMatrix();
    PVector pos = rectPos[rectIndex];
    translate(pos.x, pos.y);
    if (filled) {
      noStroke();
      fill(c, alpha);
      rect(0, 0, w, h);
    } else {
      noFill();
      strokeWeight(2.5);
      stroke(c, alpha);
      for (float y=0; y<=h; y+=step) {
        for (float x=0; x<=w; x+=step) {
          point(x, y);
        }
      }
    }
    popMatrix();
  }

  void attack() {
    speed=getVelocity();
      this.setStatus(Animation.SUSTAIN);
  }

  void sustain() {
    //rectIndex = int(random(rectPos.length));
    //show();
    this.setStatus(Animation.DECAY);
  }

  void decay() {
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