package com.stackroute.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.domain.Activities;
import com.stackroute.succour.newsapiadapter.adapter.NewsAPIAdapter;
import com.stackroute.succour.newsapiadapter.exceptions.EmptyAPIQueryURIException;
import com.stackroute.succour.newsapiadapter.exceptions.EmptyQueryParamsException;
import com.stackroute.twitterapiadapter.adapter.TwitterAPIAdapter;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class DataService {

//This is the topic where the KafkaProducer send the data.
    @Value("${kafka.topic.json}")
    private String jsonTopic;

    @Autowired
    private KafkaTemplate<String, Activities> kafkaTemplate;

    Logger logger = LoggerFactory.getLogger(DataService.class);
//Call the classes of the News Adapter and from the Twitter Adapter to get data from the adapters.
    private NewsAPIAdapter newsAPIAdapter;
    private TwitterAPIAdapter twitterAPIAdapter;
    private ObjectMapper objectMapper;
//Initialize the class objects within the constructor of the class.
    public DataService() {
        try {
            newsAPIAdapter = new NewsAPIAdapter();
           twitterAPIAdapter = new TwitterAPIAdapter();
            objectMapper = new ObjectMapper();
        } catch (IOException e) {
//            Todo Handle exception
            e.printStackTrace();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
//This method is to fetch data from the NewsAdapter.Using subscribe() method we are getting data from the adapters.
 //Here we set three query parameters as the primary domain with in the addQueryParam() method.
 //Also we implemented the send() method to send the activity data to the kafka topic.
    public void sendNews() {
        newsAPIAdapter.addQueryParam("@CGI_Global");
        newsAPIAdapter.addQueryParam("NDA");
        newsAPIAdapter.addQueryParam("Disaster");
        newsAPIAdapter.getArticleSubject().subscribe(
                article -> {
                    Activities activities = objectMapper.readValue(article.toString(), Activities.class);
                    activities.setId(UUID.randomUUID().toString());
                    activities.setTimestamp(ZonedDateTime.now().toInstant().toEpochMilli());
                    kafkaTemplate.send(jsonTopic, activities);
                },
                e -> logger.error(e.getLocalizedMessage())
        );

        try {
            newsAPIAdapter.startNewsStream();
        } catch (EmptyQueryParamsException e) {
            //TODO Handle exception
            e.printStackTrace();
        } catch (EmptyAPIQueryURIException e) {
            e.printStackTrace();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    //This method is to fetch data from the TwitterAdapter.Using subscribe() method we are getting data from the adapters.
    //Here we set three query parameters as the primary domain with in the addQueryParam() method.
    public void sendTweets() {
        try {
            twitterAPIAdapter.setSchedulerInterval(30);
            twitterAPIAdapter.addQueryParam("@CGI_Global");
            twitterAPIAdapter.addQueryParam("NDA");
            twitterAPIAdapter.addQueryParam("Disaster");
            twitterAPIAdapter.getTweets().subscribe(
                    tweet -> {
                        Activities activities = objectMapper.readValue(tweet.toString(), Activities.class);
                        activities.setId(UUID.randomUUID().toString());
                        activities.setTimestamp(ZonedDateTime.now().toInstant().toEpochMilli());
                        kafkaTemplate.send(jsonTopic, activities);
                    },
                    e -> logger.error(e.getLocalizedMessage())
            );
            twitterAPIAdapter.startTweetsStream();
        } catch (com.stackroute.twitterapiadapter.exceptions.EmptyQueryParamsException e) {
            e.printStackTrace();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
