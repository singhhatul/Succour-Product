package com.stackroute.service;

import com.stackroute.domain.Domain;
import com.stackroute.domain.DomainObject;
import com.stackroute.domain.SentimentDomain;
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
public class DomainNameSetter {
    Domain domain = new Domain();
    @Autowired
    KafkaTemplate<String, Domain> kafkaTemplate;

    @KafkaListener(topics = "domain", groupId = "json", containerFactory = "kafkaListener")
    public void domainName(SentimentDomain activities) throws IOException {
        DomainNameSetter DomainNameSetter = new DomainNameSetter();
        String string = activities.getObject().getContent();
        String actor = activities.getActor();
        String domainName = "general";
        if (actor.equals("NDA")) {
            if (DomainNameSetter.another(string, "all1.read", "BJP.read")) {
                domainName = "BJP";
            } else if (DomainNameSetter.another(string, "all1.read", "RJD.read")) {
                domainName = "RJD";
            }
            Domain NDA = new Domain(activities.getId(), activities.getTimestamp(), activities.getActor(), activities.getVerb(), new DomainObject(activities.getObject().getObjectType(), activities.getObject().getContent()), activities.getSentimentScore(), activities.getSentimentTimeStamp(), domainName);
            kafkaTemplate.send("NDA", NDA);
        }
        if (actor.equals("@CGI_Global")) {
            if (DomainNameSetter.another(string, "all.read", "finance.read")) {
                domainName = "Finance";
            }
            if (DomainNameSetter.another(string, "all.read", "legal.read")) {
                domainName = "Legal";
            }
            if (DomainNameSetter.another(string, "all.read", "hrmanagement.read")) {
                domainName = "HRM";
            }
            Domain CGI = new Domain(activities.getId(), activities.getTimestamp(), activities.getActor(), activities.getVerb(), new DomainObject(activities.getObject().getObjectType(), activities.getObject().getContent()), activities.getSentimentScore(), activities.getSentimentTimeStamp(), domainName);
            kafkaTemplate.send("CGI", CGI);
        }
        if (actor.equals("Disaster")) {
            if (DomainNameSetter.another(string, "all2.read", "Earthquake.read")) {
                domainName = "Earthquake";
            }
            if (DomainNameSetter.another(string, "all2.read", "Floods.read")) {
                domainName = "Floods";
            }
            if (DomainNameSetter.another(string, "all2.read", "Fire.read")) {
                domainName = "Fire";
            }
            Domain Disaster = new Domain(activities.getId(), activities.getTimestamp(), activities.getActor(), activities.getVerb(), new DomainObject(activities.getObject().getObjectType(), activities.getObject().getContent()), activities.getSentimentScore(), activities.getSentimentTimeStamp(), domainName);
            kafkaTemplate.send("Disaster", Disaster);
        }
    }

    public String[] getArray(String mainString) throws IOException {
        InputStream inputStream = DomainNameSetter.class.getClassLoader().getResourceAsStream("app.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String property = properties.getProperty(mainString);
        String[] split = property.split(",");
        return split;
    }

    public String[] split(String string) {
        String[] split = string.split("\\s|,");
        return split;
    }

    public boolean another(String string, String mainProperty, String domainProperty) throws IOException {
        DomainNameSetter DomainNameSetter = new DomainNameSetter();
        for (String s : DomainNameSetter.split(string)) {
            for (String s1 : DomainNameSetter.getArray(mainProperty)) {
                for (String s2 : DomainNameSetter.getArray(domainProperty)) {
                    if (s1.equals(s2) && s.equals(s2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}