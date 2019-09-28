package com.stackroute.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.domain.Domain;
import com.stackroute.domain.Email;
import com.stackroute.domain.Status;
import lombok.extern.slf4j.Slf4j;
//import org.apache.xalan.xsltc.DOM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
@Slf4j
public class EmailService {
    @Autowired
    KafkaTemplate<String, Email> KafkaTemplate;
    ObjectMapper objectMapper = new ObjectMapper();
    long count = 1;
    Status disaster = new Status();

    @KafkaListener(topics = "Disaster", groupId = "json")
    public void sendEmail(Domain domain) throws ArithmeticException, IOException {

        String actor = domain.getActor();
        log.info(actor);
        String name = domain.getDomainName();
        double sentimentScore = domain.getSentimentScore();
        if (count < 1000) {
            if (sentimentScore == 1.0) {
                int oldNegativeCount = disaster.getNegativeCount();
                disaster.setNegativeCount(++oldNegativeCount);
            }
            if (sentimentScore == 2.0) {
                int oldNeutralCount = disaster.getNeutralCount();
                disaster.setNeutralCount(++oldNeutralCount);
            }
            if (sentimentScore == 3.0 || sentimentScore == 4.0) {
                int oldPositiveCount = disaster.getPositiveCount();
                disaster.setPositiveCount(++oldPositiveCount);
            }

            count++;
        }
        double a = disaster.getPositiveCount();
        double b = disaster.getNegativeCount();
        double d = disaster.getNeutralCount();
        long c = count;
        float positive = (float) (a / c);
        float negative = (float) (b / c);
        float neutral = (float) (d / c);
        InputStream inputStream = EmailService.class.getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        log.info(inputStream + "from app");
        String domainEmail = properties.getProperty("General-Disaster");
        if (domain.getDomainName().equals("Earthquake")) {
            domainEmail = properties.getProperty("Earthquake");

        }
        if (domain.getDomainName().equals("Floods")) {
            domainEmail = properties.getProperty("Floods");

        }
        if (domain.getDomainName().equals("Fire")) {
            domainEmail = properties.getProperty("Fire");

        }

        EmailService emailService = new EmailService();

        if (count == 200) {
            String text = "report:" + " domain-name: " + domain.getDomainName() + ", count=" + count + ", positive count = " + disaster.getPositiveCount() + ", positive average = " + positive + ", negative count = " + disaster.getNegativeCount() + ", negative average = " + negative + ", neutral count = " + disaster.getNeutralCount() + ", neutral average = " + neutral;
            Email email1 = new Email(domainEmail, "alert This is the daily report based on sentimental scores ", text);
            KafkaTemplate.send("Mailservice", email1);

        }
    }


}


