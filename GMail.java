import javax.swing.JApplet;
import java.util.List;
import com.googlecode.gmail4j.GmailClient;
import com.googlecode.gmail4j.GmailConnection;
import com.googlecode.gmail4j.GmailMessage;
import com.googlecode.gmail4j.http.HttpGmailConnection;
import com.googlecode.gmail4j.rss.RssGmailClient;
import com.googlecode.gmail4j.util.LoginDialog;
 
public class GMail {
 
 private GmailClient client;
 
 /**
  * Constructor de la clase. Pide las credenciales de gmail al usuario
  */
 public GMail() {
 
  client = new RssGmailClient();
  GmailConnection connection = new HttpGmailConnection(LoginDialog
    .getInstance().show("Enter Gmail Login"));
  client.setConnection(connection);
 
 }
 
 /**
  * Método que devuelve el número de mensajes no leídos.
  */
 public int iUnreadMessages() {
  final List&ltgmailmessage> messages = client.getUnreadMessages();
  return messages.size();
 }
}