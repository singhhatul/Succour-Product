package com.stackroute.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.domain.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

//indicates it is a service layer
@Service
public class NotificationService {
//    @Value("${stakeholder.cgi}")
//    String cgi;
//    @Value("${stakeholder.nda}")
//    String nda;
//    @Value("${stakeholder.disaster}")
//    String disaster;
    //It enables to send mails
    public JavaMailSender javaMailSender;

    //AMQP (Advanced Message Queuing Protocol) is the protocol used by RabbitMQ for messaging.
    public NotificationService() {

    }//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.stackroute.kafkamailservice.config");

    private EmailService emailService;
//    String strCGI="This the daily alert for CGI, Where in these observations were found as follows:";
//    String strNDA="This the daily alert for NDA, Where in these observations were found as follows:";
//    String strDisaster="This the daily alert for Disaster, Where in these observations were found as follows:";

    @Autowired
    public NotificationService(JavaMailSender javaMailSender, EmailService emailService) {
        this.javaMailSender = javaMailSender;
        this.emailService = emailService;

    }

    //performing kafkaListener
    @KafkaListener(topics = "Mailservice", groupId = "json")
    //@KafkaListener(topics = "finance",groupId = "json")
    public void notificationSender(Email recieverMessage) throws MailException {
//        ObjectMapper objectMapper = new ObjectMapper();
        //System.out.println("hello" + recieverMessage);
        //send mail The class having to,from,cc,subject,text
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setTo("n.nandasingh221@gmail.com");
//        if (recieverMessage.clientName == "CGI") {
//            simpleMailMessage.setText((strCGI+"\n No.ofTweetsCount: "+recieverMessage.getCount()+"\n Positive Count: "+recieverMessage.getPositiveCount()+"\n NegativeCount: "+recieverMessage.getNegativeCount() +"\n PositiveAverage: "+recieverMessage.getPositiveAverage() +"\n NegativeAverage: "+recieverMessage.getNegativeAverage() +"\n NeutralCount: "+recieverMessage.getNeutralCount()+"\n NeutralAverage: " +recieverMessage.getNeutralAverage()));
//            simpleMailMessage.setTo(cgi);
//
//        } else if (recieverMessage.getClientName().equals("NDA")) {
//            simpleMailMessage.setText((strNDA+"\n No.ofTweetsCount: "+recieverMessage.getCount()+"\n Positive Count: "+recieverMessage.getPositiveCount()+"\n NegativeCount: "+recieverMessage.getNegativeCount() +"\n PositiveAverage: "+recieverMessage.getPositiveAverage() +"\n NegativeAverage: "+recieverMessage.getNegativeAverage() +"\n NeutralCount: "+recieverMessage.getNeutralCount()+"\n NeutralAverage: " +recieverMessage.getNeutralAverage()));
//            simpleMailMessage.setTo(nda);
//        } else {
            simpleMailMessage.setText(recieverMessage.getText());

            simpleMailMessage.setTo(recieverMessage.getTo());
//        }
        simpleMailMessage.setFrom("succoursr@gmail.com");
        simpleMailMessage.setSubject(recieverMessage.getSubject());



        emailService.save(recieverMessage);
        //sending mail
        javaMailSender.send(simpleMailMessage);
    }
    }
