package com.stackroute.controller;

import com.stackroute.domain.Domain;
import com.stackroute.service.CassandraSinkService;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("activity/")
@Slf4j
public class DataRetrieveController {
    @Autowired
    CassandraSinkService cassandraSinkService;

    @GetMapping("client/{domainName}")
    public ResponseEntity<List> getData(@PathVariable String domainName, @RequestParam Timestamp timeStamp, @RequestParam int limit) {
        log.debug("Domain " + domainName);
        log.debug("Time stamp: " + timeStamp);
        log.debug("Limit: " + limit);
        List<Domain> dataRetrieved = cassandraSinkService.retrieveProcessedData(domainName, timeStamp, limit);
        return new ResponseEntity<>(dataRetrieved, HttpStatus.OK);
    }


    @GetMapping("count/domainname/{domainName}")
    public ResponseEntity<Integer> getCountByDomainName(@PathVariable String domainName){
        Integer countOfTweetsByDomainName=this.cassandraSinkService.retrieveCountByDomainName(domainName);
        return new ResponseEntity<>(countOfTweetsByDomainName,HttpStatus.OK);
    }


    @GetMapping("count/actor/{actor}")
    public ResponseEntity<Integer> getCountByActor(@PathVariable String actor){
        Integer countOfTweetsByActor=this.cassandraSinkService.retrieveCountByActor(actor);
        return new ResponseEntity<>(countOfTweetsByActor,HttpStatus.OK);
    }


    @RequestMapping(path = "/prometheus")
    public void metrics(Writer responseWriter) throws IOException {
        TextFormat.write004(responseWriter, CollectorRegistry.defaultRegistry.metricFamilySamples());
        responseWriter.close();
    }
}
