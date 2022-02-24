package com.example.s13firstspring.controllers;

import com.example.s13firstspring.services.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Controller
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    public static final String EMAIL_SENDER="peshou2@gmail.com";


    @PostMapping("/forgot_password")
    @Transactional
    public String processForgotPassword(HttpServletRequest request,@RequestParam String email) {

        String token = RandomString.make(30);
       // userService.updateResetPasswordToken(token,email);
        sendEmail(email);
        return "A token has been sent to "+email+", check your inbox for further instructions" ;
    }

    public void sendEmail(String email) {

            String to = email;
            String from = EMAIL_SENDER;
            String host = "localhost";//or IP address

            //Get the session object
            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);
            Session session = Session.getDefaultInstance(properties);

            //compose the message
            try{
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
                message.setSubject("Ping");
                message.setText("Hello, this is example of sending email  ");

                // Send message
                Transport.send(message);
                System.out.println("message sent successfully....");

            }catch (MessagingException mex) {mex.printStackTrace();}
    }

}
