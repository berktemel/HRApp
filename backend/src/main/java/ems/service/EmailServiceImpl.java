package ems.service;

import ems.module.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public String sendEmail(EmailDetails emailDetails) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(from);
            simpleMailMessage.setTo(emailDetails.getTo());
            simpleMailMessage.setSubject(emailDetails.getSubject());
            simpleMailMessage.setText(emailDetails.getBody());

            javaMailSender.send(simpleMailMessage);
            return "Mail sent.";
        } catch (Exception e) {
            return "Error.";
        }
    }
}
