package com.marcuswhocodes.alert_service.service;

import com.marcuswhocodes.alert_service.domain.entity.Alert;
import com.marcuswhocodes.alert_service.repository.AlertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final AlertRepository alertRepository;
    public EmailService(JavaMailSender mailSender, AlertRepository alertRepository) {
        this.mailSender = mailSender;
        this.alertRepository = alertRepository;
    }



    public void sendMail(String to, String subject, String body, Long userId){
        log.info("Sending mail to: {}, subject: {}", to, subject);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("noreply@marcuswhocodes.com");

        try{
            mailSender.send(message);
            final Alert alertSent = Alert.builder()
                    .sent(true)
                    .createdAt(java.time.LocalDateTime.now())
                    .userId(userId)
                    .build();
            alertRepository.saveAndFlush(alertSent);
        }catch(MailException e){
            log.info("Error sending mail to: {}", to, e);
            final Alert alertSent = Alert.builder()
                    .sent(false)
                    .createdAt(java.time.LocalDateTime.now())
                    .userId(userId)
                    .build();
            alertRepository.saveAndFlush(alertSent);

        }

        log.info("Sent mail to: {}, subject: {}", to, subject);
    }
}
