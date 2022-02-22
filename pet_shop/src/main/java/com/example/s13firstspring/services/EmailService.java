package com.example.s13firstspring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService{
    @Autowired
    private JavaMailSender mailSender;

    public void sendMessage(String to,String subject, String text){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("peshou2gmail.com");
        message.setFrom(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        System.out.println("Mail send..");
    }
}
