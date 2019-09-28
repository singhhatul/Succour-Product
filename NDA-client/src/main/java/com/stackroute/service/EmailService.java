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
    Status cgiStatus = new Status();

    @KafkaListener(topics = "NDA", groupId = "json")
    public void sendEmail(Domain domain) throws ArithmeticException, IOException {

        String actor = domain.getActor();
        log.info(actor);
        String name = domain.getDomainName();
        double sentimentScore = domain.getSentimentScore();
        if (count < 1000) {
            if (sentimentScore == 1.0) {
                int oldNegativeCount = cgiStatus.getNegativeCount();
                cgiStatus.setNegativeCount(++oldNegativeCount);
            }
            if (sentimentScore == 2.0) {
                int oldNeutralCount = cgiStatus.getNeutralCount();
                cgiStatus.setNeutralCount(++oldNeutralCount);
            }
            if (sentimentScore == 3.0 || sentimentScore == 4.0) {
                int oldPositiveCount = cgiStatus.getPositiveCount();
                cgiStatus.setPositiveCount(++oldPositiveCount);
            }

            count++;
        }
        double a = cgiStatus.getPositiveCount();
        double b = cgiStatus.getNegativeCount();
        double d = cgiStatus.getNeutralCount();
        long c = count;
        float positive = (float) (a / c);
        float negative = (float) (b / c);
        float neutral = (float) (d / c);
        InputStream inputStream = EmailService.class.getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        log.info(inputStream + "from app");
        String domainEmail = properties.getProperty("General-NDA");
        if (domain.getDomainName().equals("BJP")) {
            domainEmail = properties.getProperty("BJP");

        }
        if (domain.getDomainName().equals("RJD")) {
            domainEmail = properties.getProperty("RJD");

        }


        EmailService emailService = new EmailService();
        if (count == 200) {
            String text = "report:" + " domain-name: " + domain.getDomainName() + ", count=" + count + ", positive count = " + cgiStatus.getPositiveCount() + ", positive average = " + positive + ", negative count = " + cgiStatus.getNegativeCount() + ", negative average = " + negative + ", neutral count = " + cgiStatus.getNeutralCount() + ", neutral average = " + neutral;
            Email email1 = new Email(domainEmail, "alert This is the daily report based on sentimental scores ", text);
            KafkaTemplate.send("Mailservice", email1);

        }
    }


}


