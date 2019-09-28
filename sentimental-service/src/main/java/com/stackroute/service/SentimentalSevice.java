package com.stackroute.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.stackroute.domain.Activities;
import com.stackroute.domain.SentimentDomain;
import com.stackroute.domain.SentimentDomainObject;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Properties;

@Service
@Slf4j
public class SentimentalSevice {
    @Autowired
    private KafkaTemplate<String, SentimentDomain> kafkaTemplate;
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "cleansing", groupId = "json" )
    public void sentimentAnalysis(String domain) throws IOException {
        System.out.println(domain);
        Activities activities = objectMapper.readValue(domain, Activities.class);
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + domain);
        String string = activities.getObject().getContent();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Instant instant = timestamp.toInstant();
        Timestamp tsFromInstant = Timestamp.from(instant);
        PTBTokenizer ptbTokenizer = new PTBTokenizer<>(new StringReader(string), new CoreLabelTokenFactory(), "");
        while (ptbTokenizer.hasNext()) {
            CoreLabel coreLabel = (CoreLabel) ptbTokenizer.next();
            System.out.println(coreLabel);
        }
        SentimentalSevice sentimentalSevice = new SentimentalSevice();

        System.out.println(sentimentalSevice.findSentiment(string));
        SentimentDomain sentimentDomain = new SentimentDomain(activities.getId(),activities.getTimestamp(),activities.getActor(), activities.getVerb(),new SentimentDomainObject(activities.getObject().getObjectType(),activities.getObject().getContent()),findSentiment(string),tsFromInstant.getTime());
        kafkaTemplate.send("domain", sentimentDomain);
    }

    public double findSentiment(String line) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit,pos, parse, sentiment, lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        double mainSentiment = 0;
        double sentimentalScore = 0;
        if (line != null && line.length() > 0) {
            double longest = 0;
            Annotation annotation = pipeline.process(String.valueOf(line));
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }

            }

            sentimentalScore = Math.ceil(mainSentiment);

            return sentimentalScore;

        } else
            return 0;

    }



}


