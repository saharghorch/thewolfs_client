package esprit.javafx;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.poi.util.SystemOutLogger;

import javax.mail.Message;
import javax.mail.MessagingException;

public class SendMail {

	public void send(String email, String amountCallOption) {
		try {

			String host = "smtp.gmail.com";
			String user = "meriem.dbibi@esprit.tn";
			String pass = "noussamariem94";
			String to = email;
			String from = "meriem.dbibi@esprit.tn";
			String subject = "Account";
			String messageText = "Your Account is modified and Your new amount is : " + amountCallOption;

			boolean sessionDebug = false;
			Properties props = System.getProperties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getDefaultInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, pass);
				}
			});

			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(user));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
				message.setSubject(subject);
				message.setText(messageText);
				Transport.send(message);
			} catch (MessagingException e) {
				System.out.println("error on message");
				throw new RuntimeException(e);
			}
		}
		catch (Exception ex)
		{
			System.out.println("error on connection");
			System.out.println(ex);
		}
	}

}
