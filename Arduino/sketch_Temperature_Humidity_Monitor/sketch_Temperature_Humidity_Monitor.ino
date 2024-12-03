
#include <SoftwareSerial.h>     
SoftwareSerial espSerial(2, 3);  
#define DEBUG true


String mySSID = "your_ssid";      
String myPWD = "your_password";     
String myAPI = "xxxxxxxxxxxx";  
String myHOST = "api.thingspeak.com";
String myPORT = "80";

unsigned long lastConnectionTime = 0;          
const unsigned long postingInterval = 20000L; 


#include <Wire.h>
#include <Adafruit_BMP280.h>
#define BMP280_ADDRESS 0x76  
Adafruit_BMP280 bmp;         
unsigned bmpStatus;
float pressure;
float temperature;

void setup() {
  Serial.begin(9600);
  espSerial.begin(115200);


  sendATCommand("AT+RST", 1000, DEBUG);                                         
  sendATCommand("AT+CWMODE=1", 1000, DEBUG);                                    
  sendATCommand("AT+CWJAP=\"" + mySSID + "\",\"" + myPWD + "\"", 1000, DEBUG);  

  bmpStatus = bmp.begin(BMP280_ADDRESS);
  if (!bmpStatus) {
    Serial.println(F("Could not find a valid BMP280 sensor, check wiring or "
                     "try a different address!"));
    while (1) delay(10); 
  }


  bmp.setSampling(Adafruit_BMP280::MODE_NORMAL,     
                  Adafruit_BMP280::SAMPLING_X2,     
                  Adafruit_BMP280::SAMPLING_X16,    
                  Adafruit_BMP280::FILTER_X16,      
                  Adafruit_BMP280::STANDBY_MS_500); 
}

void loop() {
  //Send data according to the time interval you set.
  if (millis() - lastConnectionTime > postingInterval) {
    sendData();
  }
}

void sendData() {

  pressure = bmp.readPressure();
  temperature = bmp.readTemperature();

  if (isnan(pressure) || isnan(temperature)) {
    Serial.println("Failed to read from BMP sensor!");
    return;
  }

  String sendData = "GET /update?api_key=" + myAPI;
  sendData += "&field1=" + String(temperature);
  sendData += "&field2=" + String(pressure);

  sendATCommand("AT+CIPMUX=1", 1000, DEBUG);                                         
  sendATCommand("AT+CIPSTART=0,\"TCP\",\"" + myHOST + "\"," + myPORT, 1000, DEBUG);  
  sendATCommand("AT+CIPSEND=0," + String(sendData.length() + 4), 1000, DEBUG);       
  espSerial.find(">");                                                         
  espSerial.println(sendData);                                                 
  Serial.println(sendData);


  Serial.println("Value to be sent: ");
  printBMP();  

  sendATCommand("AT+CIPCLOSE=0", 1000, DEBUG);  
  lastConnectionTime = millis();         
}

void sendATCommand(String command, const int timeout, boolean debug) {
  // Print and send command
  Serial.print("AT Command ==> ");
  Serial.print(command);
  Serial.println();
  espSerial.println(command);  // Send the AT command

  // Get response
  String response = "";
  long int time = millis();
  while ((time + timeout) > millis()) {  
    while (espSerial.available()) {
      char c = espSerial.read();
      response += c;
    }
  }

 
  if (debug) {
    Serial.println(response);
    Serial.println("--------------------------------------");
  }

}

void printBMP() {
  Serial.print("Temperature: ");
  Serial.print(temperature);
  Serial.print(" °C.  Pressure: ");
  Serial.print(pressure);
  Serial.println(" hPa");
  Serial.println();
}
