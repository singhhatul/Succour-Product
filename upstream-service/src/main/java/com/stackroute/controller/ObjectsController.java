package com.stackroute.controller;

import com.stackroute.domain.TransactionDetails;
import com.stackroute.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ObjectsController {

    @Autowired
    DataService dataService;



    @Autowired
    KafkaTemplate<String,TransactionDetails> kafkaTemplate1;
//
//    public ObjectsController(FinanceService financeService) {
//        this.financeService = financeService;
//    }

    @PostMapping("/transactions")
    public void saveTransaction(@RequestBody TransactionDetails transactionDetails){
        System.out.println(transactionDetails);
       kafkaTemplate1.send("UpstreamFinance",transactionDetails);
    }

//    @GetMapping("/hey")
//    public ResponseEntity<?> saveTrack()  {
//
//        dataService.send();
//        return new ResponseEntity<>("success", HttpStatus.OK);
//    }

//    @PostConstruct
//    public void myMethod() {
//        dataService.send();
//    }
}
