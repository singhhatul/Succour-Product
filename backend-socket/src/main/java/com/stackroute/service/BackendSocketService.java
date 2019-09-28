package com.stackroute.service;


import com.stackroute.domain.Domain;
import com.stackroute.domain.Finance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


//The service class implements all the methods for getting data from the kafka topics and to send the data to the frontend
//microservice.SimpleMessagingTemplate is used to send the data from backend to the frontend.
@Service
public class BackendSocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    //This method is to listen the CGI kafka topic and send the data to frontend succour-client microservice.
    @KafkaListener(topics = "CGI", groupId = "CGI", containerFactory = "kafkaListener")
    public void listenCGIData(Domain domain) throws NullPointerException {
        String content = domain.getObject().getContent();

        System.out.println(domain);
        simpMessagingTemplate.convertAndSend("/topic/CGI", domain);
    }

    //This method is to listen the NDA kafka topic and send the data to frontend succour-client microservice.
    @KafkaListener(topics = "NDA", groupId = "NDA", containerFactory = "kafkaListenerNdaContainerFactory")
    public void listenNDAData(Domain domain) throws NullPointerException {
        String content = domain.getObject().getContent();

        System.out.println(domain);
        simpMessagingTemplate.convertAndSend("/topic/NDA", domain);
    }

    //This method is to listen the Disaster kafka topic and send the data to frontend succour-client microservice.
    @KafkaListener(topics = "Disaster", groupId = "Disaster", containerFactory = "kafkaListenerDisaster")
    public void specialCharacterRemover(Domain domain) throws NullPointerException {
        String content = domain.getObject().getContent();


        System.out.println(domain);
        simpMessagingTemplate.convertAndSend("/topic/Disaster", domain);
    }
    //This method is to listen the finance kafka topic and send the data to frontend succour-client microservice.
    @KafkaListener(topics = "finance", groupId = "finance", containerFactory = "kafkaListenerfinance")
    public void financeListener(Finance domain) throws NullPointerException {
        String content = domain.getObject().getContent();

        System.out.println(domain);
        simpMessagingTemplate.convertAndSend("/topic/Finance", domain);
    }
}
