#define BLYNK_TEMPLATE_ID "TMPL2Yg_jnuZh"
#define BLYNK_TEMPLATE_NAME "Quickstart Template"
#define BLYNK_AUTH_TOKEN "NstZaaDEsq5B37zzHzyVXP-8XeiRXlpS"

#define BLYNK_PRINT Serial


#include <ESP8266_Lib.h>
#include <BlynkSimpleShieldEsp8266.h>



char ssid[] = "Split";
char pass[] = "memesonly!";  


#include <SoftwareSerial.h>
SoftwareSerial EspSerial(2, 3);  
#define ESP8266_BAUD 115200
ESP8266 wifi(&EspSerial);


const int sensorPin = 8;
int state = 0;
int awayHomeMode = 0;


BlynkTimer timer;

void setup() {

  pinMode(sensorPin, INPUT); 

  Serial.begin(115200);
  EspSerial.begin(ESP8266_BAUD);
  delay(10);


  Blynk.config(wifi, BLYNK_AUTH_TOKEN);
  Blynk.connectWiFi(ssid, pass);

  timer.setInterval(1000L, myTimerEvent);
}

void loop() {
  Blynk.run();
  timer.run();
}


BLYNK_CONNECTED() {
  Blynk.syncVirtual(V0);
}


BLYNK_WRITE(V0) {
  awayHomeMode = param.asInt();

  if (awayHomeMode == 1) {
    Serial.println("The switch on Blynk has been turned on.");
    Blynk.virtualWrite(V1, "Detecting signs of intrusion...");
  } else {
    Serial.println("The switch on Blynk has been turned off.");
    Blynk.virtualWrite(V1, "Away home mode close");
  }
}

void myTimerEvent() {

  sendData();
}


void sendData() {
  if (awayHomeMode == 1) {
    state = digitalRead(sensorPin);

    Serial.print("state:");
    Serial.println(state);

 
    if (state == HIGH) {
      Serial.println("Somebody here!");
      Blynk.virtualWrite(V1, "Somebody in your house! Please check!");
      Blynk.logEvent("intrusion_detected");
    }
  }
}
