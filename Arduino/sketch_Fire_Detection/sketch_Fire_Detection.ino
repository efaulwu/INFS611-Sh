#define BLYNK_TEMPLATE_ID "TMPL2Yg_jnuZh"
#define BLYNK_TEMPLATE_NAME "Quickstart Template"
#define BLYNK_AUTH_TOKEN "NstZaaDEsq5B37zzHzyVXP-8XeiRXlpS"
#define BLYNK_PRINT Serial


#include <ESP8266_Lib.h>
#include <BlynkSimpleShieldEsp8266.h>

char auth[] = BLYNK_AUTH_TOKEN;

char ssid[] = "Split";
char pass[] = "memesonly!";

#include <SoftwareSerial.h>
SoftwareSerial EspSerial(2, 3);

#define ESP8266_BAUD 115200

ESP8266 wifi(&EspSerial);



void setup()
{
 
  Serial.begin(115200);
  EspSerial.begin(ESP8266_BAUD);
  delay(10);

  Blynk.begin(auth, wifi, ssid, pass);
  
}

void loop()
{
  Blynk.run();

}
