/**
 * Clase principal, inicia un hilo que comprueba cada media hora si hay mensajes
 * nuevos y los envía al arduíno a través del puerto serie.
 */
public class Main {
 
 public static void main(String[] args) throws Exception {
  Thread t = new Thread() {
   public void run() {
    SerialTest serial = new SerialTest();
    if (serial.initialize() == false)
     return;
 
    GMail g = new GMail();
    try {
     while (true) {
      System.out.print("Checking...");
      serial.vSendSerial(g.iUnreadMessages());
      System.out.println("DONE!");
      // Comprobamos cada media hora
      Thread.sleep(1800);
     }
    } catch (Exception e) {
     e.printStackTrace();
    }
   }
  };
  t.start();
 }
 
}