package com.stackroute.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.domain.Domain;
import com.stackroute.domain.Notifier;

import com.stackroute.domain.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@Service
@Slf4j
public class NotificationService {

    @Autowired
    KafkaTemplate<String, Notifier> KafkaTemplate1;

    ObjectMapper objectMapper = new ObjectMapper();
    long count = 1;
    Status cgiStatus = new Status();

    @KafkaListener(topics = "Disaster", groupId = "json")
    public void sendNotification(Domain domain) throws ArithmeticException, IOException {

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
        if(count==2) {

            String text = "report:" + " domain-name: " + domain.getDomainName() + ", count=" + count + ", positive count = " + cgiStatus.getPositiveCount() + ", positive average = " + positive + ", negative count = " + cgiStatus.getNegativeCount() + ", negative average = " + negative + ", neutral count = " + cgiStatus.getNeutralCount() + ", neutral average = " + neutral;
            Notifier notifier = new Notifier("alert This is the daily report based on sentimental scores",text);
            KafkaTemplate1.send("NotificationDisaster", notifier);
            count = 0;
        }

    }


}

