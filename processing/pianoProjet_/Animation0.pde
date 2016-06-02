class Animation0 extends Anim implements Animation 
{
  public Animation0() 
  {
    /*  
      this.getVelocity renvoie la valeur de la velocité
      cette valeur est mise à jour lorsque la note commence : "ATTACK"
           et elle est mise à jour lorsque la note finit : "DECAY"

      La rapidité à laquel la note passe 
      de DEAD à SUSTAIN et de SUSTAIN à DEAD 
      à un rapport avec la valeur de velocité.
             _________
            /|       |\
           / |       | \   
          /  |       |  \            
         /    SUSTAIN    \
        ATTACK       DECAY
    */
    super(0);
  }

  void attack()
  {
    /*
      LE DEBUT DE L'ANIM
    */
    if (/* QUAND TU VEUX : FIN DE L'ATTACK */ true) {
      this.setStatus(Animation.SUSTAIN);
    }
  }

  void sustain() {
    /*
      LE CORP DE L'ANIM
    */
  }

  void decay() {
    /*
      LA MORT DE L'ANIM
    */
    if (/* QUAND TU VEUX : FIN DU DECAY */ true) {
      this.setStatus(Animation.DEAD);
    }
  }
}