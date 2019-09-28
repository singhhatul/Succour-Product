package com.stackroute.twitterapiadapter.adapter;

import com.ibm.common.activitystreams.Activity;
import com.stackroute.twitterapiadapter.exceptions.EmptyQueryParamsException;
import com.stackroute.twitterapiadapter.exceptions.EmptySearchParametersException;
import com.stackroute.twitterapiadapter.service.TweetsFetchService;
import io.reactivex.subjects.PublishSubject;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Component
@Slf4j
public class TwitterAPIAdapter {
    private String consumerKey = "I4Ua77e1BI6O53oP3LawNTDLD";
    private String consumerSecretKey = "UCt3LeBrWPlAtZcxA9bdyPCpts9ojQ2RPLcqd5HW3JKZauQTrv";
    private String accessToken = "3229341576-dqP3uNuS0NGCOl0V7gLv0tU3s6ObZuYaf50C14s";
    private String accessTokenSecret = "6eTwILDuCeu1poc02JKqJvJ0auWQf4r9hzUQDF8FdCfqf";
    private List<SearchParameters> queryParams;
    private final Twitter twitter;
    private SchedulerFactory schedulerFactory;
    private Scheduler scheduler;
    private JobDetail tweetsFetchJob;
    private Trigger trigger;
    private PublishSubject<Activity> tweetsPublishSubject;
    private int schedulerInterval = 30;
    private int fetchTweetsCount = 100;

    public TwitterAPIAdapter() throws SchedulerException {
        twitter = new TwitterTemplate(consumerKey, consumerSecretKey, accessToken, accessTokenSecret);
        schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();
        trigger = newTrigger().withIdentity("tweetsFetchTrigger", "tweetsFetchGroup").startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(schedulerInterval).repeatForever()).build();
        tweetsPublishSubject = PublishSubject.create();
//        log.debug(twitter.toString());
//        SearchResults results = twitter.searchOperations().search("#spring");
//        results.getTweets().forEach(tweet -> log.debug(tweet.getText()));
    }

    public List<SearchParameters> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<SearchParameters> queryParams) {
        this.queryParams = queryParams;
    }

    public int getSchedulerInterval() {
        return schedulerInterval;
    }

    public void setSchedulerInterval(int schedulerInterval) {
        this.schedulerInterval = schedulerInterval;
    }

    public PublishSubject<Activity> getTweets() {
        return tweetsPublishSubject;
    }

    /**
     * Add query to the queryParams list.
     *
     * @param queryParam String to be added to query params list.
     */
    public void addQueryParam(String queryParam) throws EmptyQueryParamsException {
        /*
         * Check if queryParams list is empty and if it's empty then
         * initialize it.
         * */
        if (this.queryParams == null) {
            this.queryParams = new ArrayList<>();
        }
        if (!queryParam.isBlank() && !queryParam.isEmpty()) {
            this.queryParams.add(new SearchParameters(queryParam).lang("en").count(fetchTweetsCount));
        } else throw new EmptyQueryParamsException();
    }

    public void addSearchParamToQueryParams(SearchParameters searchParameters) throws EmptySearchParametersException {
        if (searchParameters == null) {
            if (queryParams == null) {
                this.queryParams = new ArrayList<>();
            }
            this.queryParams.add(searchParameters);
        } else throw new EmptySearchParametersException();
    }

    /**
     * Method to initialize the tweetsFetchJob with data required for TweetsFetchService.
     * Creates a JobDataMap, to which data is added.
     * The JobDataMap object is then used to build the tweetsFetchJob.
     */
    private void initTweetsFetchJob() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("twitter", twitter);
        jobDataMap.put("queryParams", this.queryParams);
        jobDataMap.put("tweetsPublishSubject", tweetsPublishSubject);
        tweetsFetchJob = newJob(TweetsFetchService.class)
                .withIdentity("tweetsFetchJob", "tweetsFetchJobGroup")
                .usingJobData(jobDataMap)
                .build();
    }

    /**
     * Add the tweetFetchJob to the scheduler.
     *
     * @throws SchedulerException
     */
    private void addJobToScheduler() throws SchedulerException {
        if (tweetsFetchJob != null && trigger != null) {
            scheduler.scheduleJob(tweetsFetchJob, trigger);
        }
    }

    /**
     * Start the scheduler.
     *
     * @throws SchedulerException
     */
    private void startTweetsFetchService() throws SchedulerException {
        scheduler.start();
    }

    /**
     * Stops the TweetsFetchService by shutting down the scheduler.
     *
     * @throws SchedulerException
     */
    private void stopTweetsFetchService() throws SchedulerException {
        scheduler.shutdown(true);
        log.info("Scheduler is shut down.");
    }

    public void startTweetsStream() throws SchedulerException, EmptyQueryParamsException {
        if (this.queryParams != null && (!this.queryParams.isEmpty())) {
            initTweetsFetchJob();
            addJobToScheduler();
            startTweetsFetchService();
        } else {
            throw new EmptyQueryParamsException();
        }
    }

    /**
     * Used to stop the tweets stream by stopping the scheduler job for TweetsFetchService.
     *
     * @throws SchedulerException
     */
    public void stopTweetsStream() throws SchedulerException {
        stopTweetsFetchService();
    }

}
