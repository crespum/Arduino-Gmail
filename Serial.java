import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
 
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
 
public class Serial {
 SerialPort serialPort;
 
 /**
  * Hay que tener en cuenta que en Ubunto 11.04, el Arduino Uno y
  * posiblemente otros modelos son reconocidos como /dev/ttyACMxx. La
  * librería RXTX solo busca en /dev/ttySxx, así que es necesario enlazar
  * ambas interfaces. Por ejemplo con el comando ln -s /dev/ttyACM0
  * /dev/ttyS33.
  */
 // los puertos que normalmente usaremos
 private static final String PORT_NAMES[] = { "/dev/tty.usbserial-A9007UX1", // Mac
   "/dev/ttyS33", // Linux
   "COM3", // Windows
 };
 private OutputStream output;
 // Milisegundos durante los que se bloquea el puerto mientras se espera a
 // que esté abierto
 private static final int TIME_OUT = 2000;
 private static final int DATA_RATE = 9600;
 
 public boolean initialize() {
  boolean bState = true;
 
  CommPortIdentifier portId = null;
  Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
 
  // Antes de nada buscamos el puerto al que está conectado el Arduino.
  while (portEnum.hasMoreElements()) {
   CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
     .nextElement();
   for (String portName : PORT_NAMES) {
    if (currPortId.getName().equals(portName)) {
     portId = currPortId;
     break;
    }
   }
  }
  if (portId == null) {
   System.out.println("Could not find COM port.");
   return false;
  }
 
  try {
   serialPort = (SerialPort) portId.open(this.getClass().getName(),
     TIME_OUT);
 
   serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
     SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
 
   output = serialPort.getOutputStream();
 
  } catch (Exception e) {
   System.err.println(e.toString());
  }
 
  return bState;
 }
 
 /**
  * Este método debería ser cargado cuando se deja de usar el puerto. Esto
  * sirve para evitar el bloqueo del puerto en plataformas como Linux.
  */
 public synchronized void close() {
  if (serialPort != null) {
   serialPort.removeEventListener();
   serialPort.close();
  }
 }
 
 public void vSendSerial(int i) throws IOException {
  output.write(String.valueOf(i).getBytes());
 }
}