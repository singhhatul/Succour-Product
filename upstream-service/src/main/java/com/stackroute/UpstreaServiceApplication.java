package com.stackroute;

import com.stackroute.service.DataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class UpstreaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpstreaServiceApplication.class, args);
        ApplicationContext context = new AnnotationConfigApplicationContext(UpstreaServiceApplication.class);
        DataService dataService = context.getBean("dataService", DataService.class);
        dataService.sendNews();
        dataService.sendTweets();
    }



}
