package com.stackroute.controller;


import com.stackroute.domain.Activities;
import com.stackroute.domain.Domain;
import com.stackroute.domain.Finance;
import com.stackroute.service.CassandraSinkService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.ArrayList;
import java.util.List;

import static com.ibm.common.activitystreams.Makers.object;


@Controller
@Slf4j

public class DataController {

    @Autowired
    CassandraSinkService cassandraSinkService;


//  List<Activities> activities = new ArrayList<>();

//    Logger logger = LoggerFactory.getLogger("DataController");

//  @PostConstruct
//  public void saveEmployees() {
//    Activity activity =
//                activity()
//                        .actor(object("person").id("acct:joe@example.org"))
//                        .object(object("note").content("my note"))
//                        .verb("post")
//                        .get();
//    kafkaTemplate.send("Succour2",activity.toString());

//        kafkaTemplate.send("Succour",new Demo("anil",22));
//    kafkaTemplate.send("Succour",new Demo("datta",19));

//  }

    @KafkaListener(topics = "upstream", groupId = "json", containerFactory = "kafkaListenerContainerFactory")
    public void listen(Activities activities) {

      log.info(activities.toString());
        List<Activities> activitiesList = new ArrayList<>();
        activitiesList.add(activities);
        cassandraSinkService.saveActivities(activitiesList);
    }

    @KafkaListener(topics = "CGI", groupId = "processed-data", containerFactory = "kafkaListener")
    public void listenFromNDA(Domain domain) {

        log.info(domain.toString());
        List<Domain> domainList = new ArrayList<Domain>();
        domainList.add(domain);
        cassandraSinkService.saveActivitiesFromDomain(domainList);
    }

    @KafkaListener(topics = "NDA", groupId = "processed-data", containerFactory = "kafkaListener")
    public void listenFromCGI(Domain activities) {
//        logger.info(activities.toString());
        List<Domain> activitiesList = new ArrayList<>();
        activitiesList.add(activities);
        cassandraSinkService.saveActivitiesFromDomain(activitiesList);
    }

    @KafkaListener(topics = "Disaster", groupId = "processed-data", containerFactory = "kafkaListener")
    public void listenFromDisaster(Domain activities) {
        log.info(activities.toString());
        List<Domain> activitiesList = new ArrayList<>();
        activitiesList.add(activities);
        cassandraSinkService.saveActivitiesFromDomain(activitiesList);
    }

    @KafkaListener(topics = "finance", groupId = "finance-data", containerFactory = "kafkaFinanceListener")
    public void listenFromFinance(Finance fiananceActivities) {
        log.info(fiananceActivities.toString());
        List<Finance> activitiesList = new ArrayList<>();
        activitiesList.add(fiananceActivities);
        cassandraSinkService.saveActivitiesFromFinance(activitiesList);
    }


//    @GetMapping("/{id}")
//    public Mono<Employe> getEmployeeById(@PathVariable int id) {
//        return employeeService.getEmployeeById(id);
//    }

//    @GetMapping("/filterByAge/{age}")
//    public Flux<Employe> getEmployeesFilterByAge(@PathVariable int age) {
//        return employeeService.getEmployeesFilterByAge(age);
//    }
}
