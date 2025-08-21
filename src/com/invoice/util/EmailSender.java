package com.invoice.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.File;
import java.util.Properties;

import jakarta.activation.*;


public class EmailSender {

	public static void sendEmailWithAttachment(String toEmail, String subject, String messageBody, String filePath) {
	    final String fromEmail = "snehal.parab2703@gmail.com";
	    final String password = "eockdpsgwotdpkaz"; // App Password

	    Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");

	    Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(fromEmail, password);
	        }
	    });
	    session.setDebug(true); // Enable SMTP debug logs

	    try {
	        System.out.println("Creating message...");
	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(fromEmail, "Your Company Name"));
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
	        message.setSubject(subject);

	        MimeBodyPart messagePart = new MimeBodyPart();
	        messagePart.setText(messageBody);

	        File file = new File(filePath);
	        if (!file.exists()) {
	            System.out.println("File not found: " + filePath);
	            return;
	        }

	        MimeBodyPart attachmentPart = new MimeBodyPart();
	        attachmentPart.attachFile(file);

	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(messagePart);
	        multipart.addBodyPart(attachmentPart);

	        message.setContent(multipart);

	        System.out.println("Sending email...");
	        Transport.send(message);
	        System.out.println("✅ Email sent successfully!");
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}

}
