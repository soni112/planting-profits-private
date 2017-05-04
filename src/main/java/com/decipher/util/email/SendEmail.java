package com.decipher.util.email;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

 

import com.decipher.util.PlantingProfitLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
/**
 *  * @author Manoj Pandya
 *  
 * This SendEmail Class is use to send email. 
 *  
 */
@Component
public class SendEmail {

	private static 	EmailConfigurator emailConfigurator;
 
	@Autowired
    public void setEmailConfigurator( EmailConfigurator emailConfig){
        emailConfigurator = emailConfig;
    }

 
	public static boolean  sendEmail(String to, String subject, String text ) {
		boolean  status=false;
		 final String from =emailConfigurator.getEmailFrom();
		String host =emailConfigurator.getEmailHost();
		String userid =emailConfigurator.getEmailUserId();
		final String password = emailConfigurator.getEmailPassword();
		PlantingProfitLogger.info("Send To from :"+from+": password :"+password+":");
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.setProperty("mail.transport.protocol", "smtps");
			props.put("mail.smtp.user", userid);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.port", "465");
			props.put("mail.smtps.auth", "true");
			Session session  = Session.getInstance( props , new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                    		from, password);// Specify the Username and the PassWord
                }
            });
			MimeMessage message = new MimeMessage(session);
			InternetAddress fromAddress = null;
			InternetAddress toAddress = null;

			try {
				fromAddress = new InternetAddress(from,"Planting Profit Service");
				toAddress = new InternetAddress(to);
			} catch (AddressException e) {

				PlantingProfitLogger.error(e);
			}
			message.setFrom(fromAddress);
			message.setRecipient(RecipientType.TO, toAddress);
			message.setSubject(subject);
			message.setContent(text,"text/html");
			Transport transport = session.getTransport("smtps");
			transport.connect(host, userid, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			PlantingProfitLogger.info("mail has been sent");
			status=true;
		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			status=false;
		}
		return status;
	}

	public static boolean  sendEmail(String to,String fromName, String subject, String text ) {
		boolean  status=false;
		 final String from =emailConfigurator.getEmailFrom();
		String host =emailConfigurator.getEmailHost();
		String userid =emailConfigurator.getEmailUserId();
		final String password = emailConfigurator.getEmailPassword();
		PlantingProfitLogger.info("Send To from :"+from+": password :"+password+":");
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.setProperty("mail.transport.protocol", "smtps");
			props.put("mail.smtp.user", userid);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.port", "465");
			props.put("mail.smtps.auth", "true");
			Session session  = Session.getInstance( props , new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                    		from, password);// Specify the Username and the PassWord
                }
            });
			MimeMessage message = new MimeMessage(session);
			InternetAddress fromAddress = null;
			InternetAddress toAddress = null;

			try {
				fromAddress = new InternetAddress(from,fromName);
				toAddress = new InternetAddress(to);
			} catch (AddressException e) {

				PlantingProfitLogger.error(e);
			}
			message.setFrom(fromAddress);
			message.setRecipient(RecipientType.TO, toAddress);
			message.setSubject(subject);
			message.setContent(text,"text/html");
			Transport transport = session.getTransport("smtps");
			transport.connect(host, userid, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			PlantingProfitLogger.info("mail has been sent");
			status=true;
		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			status=false;
		}
		return status;
	}
	 
	public static boolean  sendEmailToAllRecipients(String sendBy,String[] to, String subject, String text ){
		boolean  status=false;
		 final String from =emailConfigurator.getEmailFrom();
		String host =emailConfigurator.getEmailHost();
		String userid =emailConfigurator.getEmailUserId();
		final String password = emailConfigurator.getEmailPassword();
		PlantingProfitLogger.info("Send To from :"+from+": password :"+password+":");
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.setProperty("mail.transport.protocol", "smtps");
			props.put("mail.smtp.user", userid);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.port", "465");
			props.put("mail.smtps.auth", "true");
			Session session  = Session.getInstance( props , new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                    		from, password);// Specify the Username and the PassWord
                }
            });
			MimeMessage message = new MimeMessage(session);
			InternetAddress fromAddress = null;
			 
			InternetAddress[] toAddresses = new InternetAddress[to.length];
			try {
				fromAddress = new InternetAddress(from,sendBy);
				for(int i =0; i< to.length; i++)
				  {
					toAddresses[i] = new InternetAddress(to[i]);
				  }
			} catch (AddressException e) {

				PlantingProfitLogger.error(e);
			}
			message.setFrom(fromAddress);
			message.setRecipients(RecipientType.TO, toAddresses);
			message.setSubject(subject);
			message.setContent(text,"text/html");
			Transport transport = session.getTransport("smtps");
			transport.connect(host, userid, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			PlantingProfitLogger.info("mail has been sent");
			status=true;
		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			status=false;
		}
		return status;
	} 
}