package com.tti;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Email {
   private String to;
   private String from;
   private String asunto;
   private String cuerpo;

   public String getTo() {
	return to;
   }
   
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getAsunto() {
		return asunto;
	}
	
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	
	public String getCuerpo() {
		return cuerpo;
	}

public void setCuerpo(String cuerpo) {
	this.cuerpo = cuerpo;
}

public Email() {
	this.from = "";
	this.to = "";
	this.asunto = "";
	this.cuerpo = "";
}

public Email(String from, String to, String asunto, String cuerpo){
	this.from = from;
	this.to = to;
	this.asunto = asunto;
	this.cuerpo = cuerpo;
}

public boolean sendMail(){
	// Assuming you are sending email from localhost
    String host = "localhost";

    // Get system properties
    Properties properties = System.getProperties();

    // Setup mail server
    properties.setProperty("mail.smtp.host", host);
    properties.setProperty("mail.transport.protocol", "smtp");

    // Get the default Session object.
    Session session = Session.getDefaultInstance(properties);
    boolean isOk = true;
    try{
        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(this.from));

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress(this.to));

        // Set Subject: header field
        message.setSubject(this.asunto);

        // Now set the actual message
        message.setText(this.cuerpo);
        Transport transport = session.getTransport();
        transport.connect();
        transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO));
        transport.close();
        // Send message
//        Transport.send(message);
        System.out.println("[E-MAIL]: Enviado exitosamente a " + this.to);
     }catch (MessagingException mex) {
        mex.printStackTrace();
        isOk = false;
     }
    return isOk;
}

public boolean sendRegistroMail(String username, String password){
	// Assuming you are sending email from localhost
    String host = "localhost";

    // Get system properties
    Properties properties = System.getProperties();

    // Setup mail server
    properties.setProperty("mail.smtp.host", host);
    properties.setProperty("mail.transport.protocol", "smtp");

    // Get the default Session object.
    Session session = Session.getDefaultInstance(properties);
    boolean isOk = true;
    try{
        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(this.from));

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress(this.to));

        // Set Subject: header field
        message.setSubject("Bienvenido a TTi");

        // Now set the actual message
        message.setContent("<h1>Bienvenido a TTi</h1>" +
        				"<p>Se ha generado una contraseña para que ingreses al Sistema.</p>" +
        				"<p>Usuario: " + username + "</p>" +
        				"<p>Contraseña: " + password + "</p>" +
        						"<p><b>NO OLVIDES CAMBIAR TU CONTRASEÑA UNA VEZ HAGAS LOGIN</b></p>","text/html");

        // Send message
        Transport transport = session.getTransport();
        transport.connect();
        transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO));
        transport.close();
        System.out.println("[E-MAIL]: Enviado exitosamente a " + this.to);
     }catch (MessagingException mex) {
        mex.printStackTrace();
        isOk = false;
     }
    return isOk;
}

}

