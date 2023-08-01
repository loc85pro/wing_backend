package com.win.server.service;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

    @Service
    public class MailService {

        private String host = "smtp.gmail.com";
        private String port = "587";
        private String username = "phanxuanloc2612@gmail.com";
        private String password = "kpmultcipuitmbxg";

        public void sendEmail(String address,String content) {
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
                message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(address)});

                message.setFrom(new InternetAddress(username));
                message.setSubject("Testing email feature");
                message.setText(content);
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
