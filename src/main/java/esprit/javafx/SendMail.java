package esprit.javafx;

import java.util.Date;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;

public class SendMail {
	
	public void send(String email,String amountCallOption){
		
	try {

	    String host ="smtp.gmail.com" ;

	       String user = "meriem.dbibi@esprit.tn";

	       String pass = "noussamariem94";

	       String to =email;

	       String from = "meriem.dbibi@esprit.tn";

	       String subject = "Account";

	       String messageText = "Your new amount is : "+amountCallOption;

	       boolean sessionDebug = false;

	       Properties props = System.getProperties();

	       props.put("mail.smtp.starttls.enable", "true");

	       props.put("mail.smtp.host", host);

	       props.put("mail.smtp.port", "587");

	       props.put("mail.smtp.auth", "true");

	       props.put("mail.smtp.starttls.required", "true");

	props.put("mail.smtp.starttls.enable", "true"); 

	       java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

	       Session mailSession = Session.getDefaultInstance(props, null);

	       mailSession.setDebug(sessionDebug);

	       Message msg = new MimeMessage(mailSession);

	       msg.setFrom(new InternetAddress(from));

	       InternetAddress[] address = {new InternetAddress(to)};

	       msg.setRecipients(Message.RecipientType.TO, address);

	       msg.setSubject(subject); msg.setSentDate(new Date());

	       msg.setText(messageText);

	      Transport transport=mailSession.getTransport("smtp");

	      transport.connect(host, user, pass);

	      transport.sendMessage(msg, msg.getAllRecipients());

	      transport.close();

	      System.out.println("message send successfully");

	   }

	catch(Exception ex)

	   {

	       System.out.println(ex);

	   }
	}
}

