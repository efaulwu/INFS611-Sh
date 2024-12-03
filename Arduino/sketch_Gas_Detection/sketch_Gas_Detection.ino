const int sensorPin = A0;
int sensorValue;

const int buzzerPin = 9;

const int RPin = 5;
const int GPin = 6;

void setup() {
  Serial.begin(9600);
  pinMode(buzzerPin, OUTPUT);
  pinMode(RPin, OUTPUT);
  pinMode(GPin, OUTPUT);
}

void loop() {
 
  sensorValue = analogRead(sensorPin);
  Serial.print("Analog output: ");
  Serial.println(sensorValue);

  if (sensorValue > 300) {
    tone(buzzerPin, 500, 300);
    digitalWrite(GPin, LOW);
    digitalWrite(RPin, HIGH);
  } else {
    
    noTone(buzzerPin);
    digitalWrite(RPin, LOW);
    digitalWrite(GPin, HIGH);
  }
  
  
  delay(50);
}
