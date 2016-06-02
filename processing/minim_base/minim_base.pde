import ddf.minim.*;
import ddf.minim.analysis.*;

Minim minim;
AudioInput in;
FFT fft;
FFT fftlin;
BeatDetect beat;

float eRadius = 20;
float spectrumScale = 8;

void setup() {
  size(800, 600, P3D);
  minim = new Minim(this);
  in = minim.getLineIn();
  println(in.bufferSize());
  fft = new FFT(in.bufferSize(), in.sampleRate());
  fftlin = new FFT(in.bufferSize(), in.sampleRate());
  fftlin.linAverages(30);
  
  beat = new BeatDetect();
  beat.setSensitivity(250);
}

void draw() {
  background(0);
  stroke(255);
  for (int i=0; i<in.bufferSize()-1; i++) {
    line(i, 50+in.left.get(i)*80, i+1, 50+in.left.get(i+1)*80);
    line(i, 150+in.right.get(i)*80, i+1, 150+in.right.get(i+1)*80);
  }
  fft.forward(in.mix);
  for (int i = 0; i < fft.specSize(); i++) { 
    float avg = fft.getBand(i)*spectrumScale;
    line( i, height/2, i, height/2 - avg);
  }
  fftlin.forward(in.mix);
  int w = 9;
  noStroke();
  fill(255);
  for (int i = 0; i < fftlin.avgSize(); i++) {
    float avg = fftlin.getAvg(i)*spectrumScale;    
    rect(i*w, (height/2+100)-avg, i*w + w, avg);
  }
  
  beat.detect(in.mix);
  float a = map(eRadius,20,60,60,255);
  fill(255,a);
  if(beat.isOnset()) eRadius = 60;
  ellipse(50,height/2+200,eRadius,eRadius);
  eRadius *= .95;
  if(eRadius < 20) eRadius = 20;
}