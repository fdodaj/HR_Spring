package al.ikubinfo.hrmanagement.services;//package al.ikubinfo.hrmanagement.services;

import al.ikubinfo.hrmanagement.dto.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendMail(EmailMessage emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailMessage.getTo());
        message.setSubject(emailMessage.getSubject());
        message.setText(emailMessage.getMessage());
        emailSender.send(message);
    }
}
