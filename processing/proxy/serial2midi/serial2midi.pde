import processing.serial.*;

import themidibus.*;
import javax.sound.midi.*;

Serial myPort;
MidiBus myBus;
boolean flag = false;
int message, ms, ls;

void setup() 
{
  myPort = new Serial(this, "/dev/tty.usbmodem1451", 9600);
  myBus = new MidiBus(this);
  myBus.addOutput(1);
  frameRate(1000);
}

void draw() 
{
  if (!flag) 
  {
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

    if (ms == ShortMessage.NOTE_OFF || ms == ShortMessage.NOTE_ON) 
    {
      flag = true;
    }
  } else  if (myPort.available() > 4)
  {
    flag = false;
    try
    {
      ShortMessage mess = new ShortMessage();
      mess.setMessage(ms, ls, myPort.read(), myPort.read());
      myBus.sendMessage(mess);
    } 
    catch(InvalidMidiDataException e) {
    }
  }
}