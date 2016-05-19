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

  void attack() {
  }

  void sustain() {
  }

  void decay() {
  }

  byte getStatus() {
    return status;
  }
  void setStatus(byte s) {
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

  int getVelocity() {
    return velocity;
  }
  int setVelocity(int v) {
    return velocity = v;
  }
}

void animationSwitcher(Class c, boolean start, int velocity) {
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