#include <Wire.h>
char WHO_AM_I = 0x00;
char SMPLRT_DIV= 0x15;
char DLPF_FS = 0x16;
char GYRO_XOUT_H = 0x1D;
char GYRO_XOUT_L = 0x1E;
char GYRO_YOUT_H = 0x1F;
char GYRO_YOUT_L = 0x20;
char GYRO_ZOUT_H = 0x21;
char GYRO_ZOUT_L = 0x22;




void setup() {
  //Create a serial connection using a 9600bps baud rate.
    Serial.begin(9600);
    
    //Initialize the I2C communication. This will set the Arduino up as the 'Master' device.
    Wire.begin();
    
    //Read the WHO_AM_I register and print the result
    char id=0;
    id = itgRead(itgAddress, 0x00);
    Serial.print("ID: ");
    Serial.println(id, HEX);
    
    //Configure the gyroscope
    //Set the gyroscope scale for the outputs to +/-2000 degrees per second
    itgWrite(itgAddress, DLPF_FS, (DLPF_FS_SEL_0|DLPF_FS_SEL_1|DLPF_CFG_0));
    //Set the sample rate to 100 hz
    itgWrite(itgAddress, SMPLRT_DIV, 9);

}

void loop() {
  //Create variables to hold the output rates.
    int xRate, yRate, zRate;
    //Read the x,y and z output rates from the gyroscope.
    xRate = readX();
    yRate = readY();
    zRate = readZ();
    //Print the output rates to the terminal, seperated by a TAB character.
    Serial.print(xRate);
    Serial.print('\t');
    Serial.print(yRate);
    Serial.print('\t');
    Serial.println(zRate);
    
    //Wait 10ms before reading the values again. (Remember, the output rate was set to 100hz and 1reading per 10ms = 100hz.)
    delay(10);
}
