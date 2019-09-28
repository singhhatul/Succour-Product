package com.stackroute.service;
import com.stackroute.domain.Notifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationSockerService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @KafkaListener(topics = "NotificationCGI", groupId = "NotificationCGI", containerFactory = "NotificationkafkaListener")
    public void listenCGIData(Notifier notificationDomain) throws NullPointerException {
        String content = notificationDomain.getSubject();

        System.out.println(notificationDomain);
        simpMessagingTemplate.convertAndSend("/topic/notification/CGI", notificationDomain);
    }

  
    @KafkaListener(topics = "NotificationNDA", groupId = "NotificationNDA", containerFactory = "NotificationkafkaListenerNdaContainerFactory")
    public void listenNDAData(Notifier notificationDomain) throws NullPointerException {
        String content = notificationDomain.getSubject();

        System.out.println(content);
        simpMessagingTemplate.convertAndSend("/topic/notification/NDA", notificationDomain);
    }


    @KafkaListener(topics = "NotificationDisaster", groupId = "NotificationDisaster", containerFactory = "NotificationkafkaListenerDisaster")
    public void specialCharacterRemover(Notifier notificationDomain) throws NullPointerException {
        String content = notificationDomain.getSubject();


        System.out.println(notificationDomain);
        simpMessagingTemplate.convertAndSend("/topic/notification/Disaster", notificationDomain);
    }
}
