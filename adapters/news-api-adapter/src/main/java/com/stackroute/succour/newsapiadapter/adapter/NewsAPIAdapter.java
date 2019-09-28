package com.stackroute.succour.newsapiadapter.adapter;

import com.ibm.common.activitystreams.Activity;
import com.stackroute.succour.newsapiadapter.exceptions.EmptyAPIQueryURIException;
import com.stackroute.succour.newsapiadapter.exceptions.EmptyQueryParamsException;
import com.stackroute.succour.newsapiadapter.service.NewsFetchService;
import io.reactivex.subjects.PublishSubject;
import org.apache.http.client.utils.URIBuilder;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


/**
 * The adapter class is used to initialize and add start the NewsFetchService as a
 * scheduler job.
 * The adapter passes values required by the NewsFetchService through JobDataMap.
 * The scheduler starts executing the job whenever the startNewsFetchService() is called.
 * The job is executed for every 5 seconds and the articles form the fetched data is added
 * added to the PublishSubject<Article>.
 */
public class NewsAPIAdapter {

    private List<String> queryParams; /*List of query strings to be added to the URI */
    private static final String BASE_URI = "https://newsapi.org/v2/everything"; /*BASE_URI for newapi.org*/
    private URI apiQueryURI = null; /*To store the URI formed by URIBuilder*/
    private WebClient webClient = WebClient.create();
    private SchedulerFactory schedulerFactory;
    private Scheduler scheduler;
    private JobDetail newsFetchJob;
    private Trigger trigger;
    private PublishSubject<Activity> articlePublishSubject;
    private static final int schedulerInterval = 180;

    public NewsAPIAdapter() throws IOException, SchedulerException {
        schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();
        trigger = newTrigger().withIdentity("newsFetchTrigger", "newsFetchGroup").startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(schedulerInterval).repeatForever()).build();
        articlePublishSubject = PublishSubject.create();
    }

    public List<String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<String> queryParams) {
        this.queryParams = queryParams;
    }

    /**
     * Add query to the queryParams list.
     *
     * @param queryParam String to be added to query params list.
     */
    public void addQueryParam(String queryParam) {
        /*
         * Check if queryParams list is empty and if it's empty then
         * initialize it.
         * */
        if (this.queryParams == null) {
            this.queryParams = new ArrayList<>();
        }
        this.queryParams.add(queryParam);
    }

    /*
     * Dynamically create URI required for calling the newsapi endpoint.
     * Uses apache's HttpClient.URIBuilder
     * Dynamically appends all the arguments as parameters for the URI
     * Example query : "https://newsapi.org/v2/everything?q=Amazon fires&language=en"
     * */
    private void buildURI() throws EmptyQueryParamsException {
        try {
            URIBuilder apiURIBuilder = new URIBuilder(BASE_URI);
            queryParams.forEach(queryParam -> apiURIBuilder.addParameter("q", queryParam));
            apiURIBuilder.addParameter("language", "en");
            if (this.queryParams != null && (!this.queryParams.isEmpty())) {
                this.apiQueryURI = apiURIBuilder.build();
            } else {
                throw new EmptyQueryParamsException();
            }
        } catch (URISyntaxException e) {
            // TODO Handle exception to logger or something else
            e.printStackTrace();
        }
    }

    /**
     * Add the newsFetchJob to the scheduler.
     * @throws SchedulerException
     */
    private void addJobToScheduler() throws SchedulerException {
        if (newsFetchJob != null && trigger != null) {
            scheduler.scheduleJob(newsFetchJob, trigger);
        }
    }

    /**
     * Start the scheduler.
     * @throws SchedulerException
     */
    private void startNewsFetchService() throws SchedulerException {
        scheduler.start();
    }

    /**
     * Stops the NewsFetchService by shutting down the scheduler.
     * @throws SchedulerException
     */
    private void stopNewsFetchService() throws SchedulerException {
        scheduler.shutdown(true);
        System.out.println("Scheduler shut down");
    }

    /**
     * Used to start fetching data from newsapi.org by making repeated calls using Quartz
     * scheduler.
     * The method builds a query URI which is used by NewsFetchService to query the newapi.org server.
     * After building the URI, initialize the newsFetchJob by calling initNewsFetchJob().
     * Add the initialized newsFetchJob to the scheduler by calling addJobToScheduler().
     * Start the scheduler by calling the startNewsFetchService().
     * @throws EmptyQueryParamsException
     * @throws EmptyAPIQueryURIException
     * @throws SchedulerException
     */
    public void startNewsStream() throws EmptyQueryParamsException, EmptyAPIQueryURIException, SchedulerException {
        if (this.queryParams != null && (!this.queryParams.isEmpty())) {
            buildURI();
            /*Check if URI is not Unull*/
            if (apiQueryURI != null) {
                initNewsFetchJob();
                addJobToScheduler();
                startNewsFetchService();
            } else {
                throw new EmptyAPIQueryURIException();
            }
        } else {
            throw new EmptyQueryParamsException();
        }
    }

    /**
     * Method to initialize the newsFetchJob with data required for NewsFetchService.
     * Creates a JobDataMap, to which data is added.
     * The JobDataMap object is then used to build the newsFetchJob.
     */
    private void initNewsFetchJob() {
        if (webClient != null && apiQueryURI != null) {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("APIQueryURI", apiQueryURI);
            jobDataMap.put("webClient", webClient);
            jobDataMap.put("articlePublishSubject", articlePublishSubject);
            newsFetchJob = newJob(NewsFetchService.class)
                    .withIdentity("newsFetchJob", "newsFetchJobGroup")
                    .usingJobData(jobDataMap)
                    .build();
        }
    }

    public PublishSubject<Activity> getArticleSubject() {
        return articlePublishSubject;
    }

    /**
     * Used to stop the news stream by stopping the scheduler job for NewsFetchService.
     *
     * @throws SchedulerException
     */
    public void stopNewsStream() throws SchedulerException {

        stopNewsFetchService();
    }
}
