package com.win.server.service;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

public class MailService {
    @Service
    public class EmailService {

        private String host = "smtp.gmail.com";
        private String port = "587";
        private String username = "phanxuanloc2612@gmail.com";
        private String password = "kpmultcipuitmbxg";

        public void sendEmail() {
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", port);

            Session session = Session.getInstance(props,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            Message message = new MimeMessage(session);
            try {
                message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("phanxuanloc2612@gmail.com")});

                message.setFrom(new InternetAddress(username));
                message.setSubject("Testing email feature");
                message.setText("Hello this is your daddy");
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
