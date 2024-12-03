
#define BLYNK_TEMPLATE_ID "TMPLxxxxxxx"
#define BLYNK_TEMPLATE_NAME "Plant Monitor"
#define BLYNK_AUTH_TOKEN "xxxxxxxxxxx"


#define BLYNK_PRINT Serial

#include <ESP8266_Lib.h>               
#include <BlynkSimpleShieldEsp8266.h>  

// Your WiFi credentials.
char ssid[] = "your_ssid";
char pass[] = "your_password"; 

// Software Serial on Uno
#include <SoftwareSerial.h>
SoftwareSerial EspSerial(2, 3); 
#define ESP8266_BAUD 115200
ESP8266 wifi(&EspSerial);

// Configure DHT
#include <DHT.h>
#define DHTPIN 8           
#define DHTTYPE DHT11      
DHT dht(DHTPIN, DHTTYPE);  
float temperature;
float humidity;


#define wetSoil 320  
#define drySoil 400 
const int moistureSensorPin = A0;
int moisture;
String soilStatus;

BlynkTimer timer;

void setup() {

  Serial.begin(115200);           
  EspSerial.begin(ESP8266_BAUD);  
  delay(10);

 
  Blynk.config(wifi, BLYNK_AUTH_TOKEN);
  Blynk.connectWiFi(ssid, pass);

 
  timer.setInterval(5000L, myTimerEvent);

 
  dht.begin();
}

void loop() {
  Blynk.run();
  timer.run(); 
}

void myTimerEvent() {

  sendData(); 


void sendData() {

 
  humidity = dht.readHumidity();
  temperature = dht.readTemperature();

  if (isnan(humidity) || isnan(temperature)) {  
    Serial.println(F("Failed to read from DHT sensor!"));
    return;
  }

  moisture = analogRead(moistureSensorPin);  

  
  String color;
  if (moisture < wetSoil) {
    soilStatus = "Soil is too wet";
    color = "#D3435C";
  } else if (moisture >= wetSoil && moisture < drySoil) {
    soilStatus = "Soil moisture is perfect";
    color = "#00FF00";
  } else {
    soilStatus = "Soil is too dry - time to water!";
    color = "#D3435C";
  }

  printData();
  Blynk.virtualWrite(V0, temperature);
  Blynk.virtualWrite(V1, humidity);
  Blynk.virtualWrite(V2, soilStatus);
  Blynk.virtualWrite(V3, 255);            
  Blynk.setProperty(V3, "color", color);  
}
void printData() {
  Serial.println("----------------------data----------------------");
  Serial.print(F("Humidity: "));
  Serial.print(humidity);
  Serial.print(F("%  Temperature: "));
  Serial.print(temperature);
  Serial.println(F("°C "));
  Serial.print("(");
  Serial.print(moisture);
  Serial.print(") ");
  Serial.println(soilStatus);
}

