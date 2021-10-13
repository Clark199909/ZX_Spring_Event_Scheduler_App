package app.email;

import java.util.Properties;
import java.util.Random;

import javax.mail.*;  
import javax.mail.internet.*;  

public class Email {
	//This method uses the code from https://www.youtube.com/watch?v=BSIPHFvMjGE&t=863s, and therefore should be as a reference to this video.
	public static void sendEmail(String email, String emailContent, String emailTitle){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 465);
        //Need to make put the email address here
        props.put("mail.smtp.user", "Your Email Address");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.port", 465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false"); 
        try{
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            message.setText(emailContent);
            message.setSubject(emailTitle);
            //Need to make put the email address here
            message.setFrom(new InternetAddress("xxxxxx@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.saveChanges();
            Transport transport = session.getTransport("smtp");
            //Need to make put the email id and password here
            transport.connect("smtp.gmail.com", "xxxxxx", "password");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            
            
        }catch(Exception e){
            System.out.println(e);  
        }
    }
	
	public static String generateCode() {
		Random rd = new Random();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<6; ++i) {
			sb.append((char)(rd.nextInt(25)+'A'));
		}
		return sb.toString();
	}
}
