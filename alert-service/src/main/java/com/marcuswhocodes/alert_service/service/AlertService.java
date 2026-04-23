package com.marcuswhocodes.alert_service.service;

import com.marcuswhocodes.kafka.event.AlertingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlertService {

  private final EmailService emailService;
  public AlertService(final EmailService emailService) {
    this.emailService = emailService;
  }

  @KafkaListener(topics = "energy-alerts", groupId = "alert-service")
    public void energyUsageAlertEvent(AlertingEvent alertingEvent){
      log.info("Received alerting event: {} ", alertingEvent);
      //send email alert
      final String subject = "Energy Usage Alert for User " + alertingEvent.userId();
      final String message = "Alert: " + alertingEvent.message() + "\nThreshold:  " + alertingEvent.threshold()
              + "\nEnergy Consumed: " + alertingEvent.energyConsumed();
      emailService.sendMail(
              alertingEvent.email(),
              subject,
              message,
              alertingEvent.userId()
      );

  }

}
