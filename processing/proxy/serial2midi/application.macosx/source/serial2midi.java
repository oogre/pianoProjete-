import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import javax.sound.midi.*; 
import themidibus.*; 
import processing.serial.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class serial2midi extends PApplet {







Serial myPort;
MidiBus myBus;
boolean flag = false;
int message, ms, ls;

public void setup() 
{
  myPort = new Serial(this, "/dev/tty.usbmodem1451", 9600);
  myBus = new MidiBus(this);
  myBus.addOutput(1);
  frameRate(1000);
}

public void draw() 
{
  if (!flag) {
    if (myPort.available() > 1) 
    {
      message = myPort.read();
      ms = message & 0xF0;
      ls = message & 0x0F;
    } else
    {
      message = 0;
      ms = 0;
      ls = 0;
    }

    if (ms == 0x80 || ms == 0x90) {
      flag = true;
    }
  } else  if (myPort.available() > 4)
  {
    flag = false;
    try
    {
      ShortMessage mess = new ShortMessage();
      mess.setMessage(ms == 0x90 ? ShortMessage.NOTE_OFF : ShortMessage.NOTE_ON, ls, myPort.read(), myPort.read());
      myBus.sendMessage(mess);
    } 
    catch(InvalidMidiDataException e) {
    }
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "serial2midi" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
