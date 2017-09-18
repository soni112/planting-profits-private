package com.decipher.agriculture.service.email;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import com.decipher.util.PlantingProfitLogger;
import com.decipher.util.email.EmailConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    private final int CORE_POOL_SIZE = 100;
    private final int MAX_POOL_SIZE = 1000;
    private final long KEEP_ALIVE_TIME = 5000;

    private ExecutorService executorService = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    private static EmailConfigurator emailConfigurator;

    @Autowired
    public void setEmailConfigurator(EmailConfigurator emailConfig) {
        emailConfigurator = emailConfig;
    }


    public void sendEmail(final String to, final String subject, final String text) {
        final String from = emailConfigurator.getEmailFrom();
        final String host = emailConfigurator.getEmailHost();
        final String userid = emailConfigurator.getEmailUserId();
        final String password = emailConfigurator.getEmailPassword();
        PlantingProfitLogger.info("Send To from :" + from + ": password :" + password + ":");

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Properties props = System.getProperties();
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.setProperty("mail.transport.protocol", "smtps");
                props.put("mail.smtp.user", userid);
                props.put("mail.smtp.password", password);
                props.put("mail.smtp.port", "465");
                props.put("mail.smtps.auth", "true");
                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                from, password);// Specify the Username and the PassWord
                    }
                });
                MimeMessage message = new MimeMessage(session);
                InternetAddress fromAddress = null;
                InternetAddress toAddress = null;

                try {

                    fromAddress = new InternetAddress(from, "Planting Profit Service");
                    toAddress = new InternetAddress(to);


                    message.setFrom(fromAddress);
                    message.setRecipient(RecipientType.TO, toAddress);
                    message.setSubject(subject);
                    message.setContent(text, "text/html");

                    Transport transport = session.getTransport("smtps");
                    transport.connect(host, userid, password);
                    transport.sendMessage(message, message.getAllRecipients());

                    transport.close();
                    PlantingProfitLogger.info("mail has been sent");
                } catch (Exception e) {
                    PlantingProfitLogger.error(e);
                }
            }
        });

    }

    public void sendEmail(final String to, final String fromName, final String subject, final String text) {

        final String from = emailConfigurator.getEmailFrom();
        final String host = emailConfigurator.getEmailHost();
        final String userid = emailConfigurator.getEmailUserId();
        final String password = emailConfigurator.getEmailPassword();
        PlantingProfitLogger.info("Send To from :" + from + ": password :" + password + ":");
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Properties props = System.getProperties();
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.setProperty("mail.transport.protocol", "smtps");
                props.put("mail.smtp.user", userid);
                props.put("mail.smtp.password", password);
                props.put("mail.smtp.port", "465");
                props.put("mail.smtps.auth", "true");
                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                from, password);// Specify the Username and the PassWord
                    }
                });
                MimeMessage message = new MimeMessage(session);
                InternetAddress fromAddress = null;
                InternetAddress toAddress = null;

                try {
                    fromAddress = new InternetAddress(from, fromName);
                    toAddress = new InternetAddress(to);

                    message.setFrom(fromAddress);
                    message.setRecipient(RecipientType.TO, toAddress);
                    message.setSubject(subject);
                    message.setContent(text, "text/html");
                    Transport transport = session.getTransport("smtps");
                    transport.connect(host, userid, password);
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();
                    PlantingProfitLogger.info("mail has been sent");
                } catch (Exception e) {
                    PlantingProfitLogger.error(e);
                }
            }
        });
    }

    public void sendEmailToMultipleRecipients(final String sendBy, final String[] to, final String subject, final String text) {

        final String from = emailConfigurator.getEmailFrom();
        final String host = emailConfigurator.getEmailHost();
        final String userid = emailConfigurator.getEmailUserId();
        final String password = emailConfigurator.getEmailPassword();
        PlantingProfitLogger.info("Send To from :" + from + ": password :" + password + ":");

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                Properties props = System.getProperties();
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.setProperty("mail.transport.protocol", "smtps");
                props.put("mail.smtp.user", userid);
                props.put("mail.smtp.password", password);
                props.put("mail.smtp.port", "465");
                props.put("mail.smtps.auth", "true");
                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                from, password);// Specify the Username and the PassWord
                    }
                });
                MimeMessage message = new MimeMessage(session);
                InternetAddress fromAddress = null;

                InternetAddress[] toAddresses = new InternetAddress[to.length];
                try {
                    fromAddress = new InternetAddress(from, sendBy);
                    for (int i = 0; i < to.length; i++) {
                        toAddresses[i] = new InternetAddress(to[i]);
                    }

                    message.setFrom(fromAddress);
                    message.setRecipients(RecipientType.TO, toAddresses);
                    message.setSubject(subject);
                    message.setContent(text, "text/html");
                    Transport transport = session.getTransport("smtps");
                    transport.connect(host, userid, password);
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();

                    PlantingProfitLogger.info("mail has been sent");

                } catch (Exception e) {
                    PlantingProfitLogger.error(e);
                }
            }
        });
    }
}