byte seven_seg_digits[11][8] = { { 0,1,1,1,0,1,1,1 },  // = 0
                                 { 0,1,1,0,0,0,0,0 },  // = 1
                                 { 0,0,1,1,1,0,1,1 },  // = 2
                                 { 0,1,1,1,1,0,0,1 },  // = 3
                                 { 0,1,1,0,1,1,0,0 },  // = 4
                                 { 0,1,0,1,1,1,0,1 },  // = 5
                                 { 0,1,0,1,1,1,1,1 },  // = 6
                                 { 0,1,1,1,0,0,0,0 },  // = 7
                                 { 0,1,1,1,1,1,1,1 },  // = 8
                                 { 0,1,1,1,1,1,0,1 },  // = 9
                                 { 1,1,1,1,0,1,1,1 }   // = 10+
                                                  };
 
void setup() {   
  //Iniciamos el puerto serie a esa tasa
  Serial.begin(9600);
   
  //Pins del 7 segmentos
  pinMode(2,  OUTPUT);  
  pinMode(3,  OUTPUT);
  pinMode(4,  OUTPUT);
  pinMode(5,  OUTPUT);
  pinMode(6,  OUTPUT);
  pinMode(7,  OUTPUT);
  pinMode(8,  OUTPUT);  
  pinMode(9,  OUTPUT); }
   
  //Variable en donde se recibiran los datos
  char inByte[] = "00";
  int i;
  //Para determinar si el numero recibido tiene
  //mas de una cifra y mostrar correctamente
  //en el display
  boolean bBig = true;
 
void loop() {
  i=0;
  //leemos del puerto serie
  while(Serial.available() > 0){
    if (i > 0) bBig = true;
    else bBig = false;
         
    inByte[i] = Serial.read();        
    i++;
    //Para evitar errores en la lectura
    delay(1);
 
  }
 
  //si el numero recibido es menor que 10 se muestra
  //directamente en el 7 segmentos
  if(!bBig)
       sevenSegWrite(inByte[0]-48);
  //en caso contrario se muestra 0.     
  else
       sevenSegWrite(10);
 
}
 
void sevenSegWrite(byte digit) {
  for (byte segCount = 0; segCount < 8; ++segCount)
    digitalWrite(segCount+2, seven_seg_digits[digit][segCount]);
}