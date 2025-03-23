package com.restapi.apirestmoviles.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String to, String otpCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Your OTP Code - GreenDrive");
            helper.setText(
                    "<h2>Hello!</h2>" +
                            "<p>Your OTP code is:</p>" +
                            "<h1 style='color: green'>" + otpCode + "</h1>" +
                            "<p>This code will expire in 10 minutes.</p>" +
                            "<br><p>GreenDrive Team</p>",
                    true);

            mailSender.send(message);
            System.out.println("OTP email sent to: " + to);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
        }
    }
}
