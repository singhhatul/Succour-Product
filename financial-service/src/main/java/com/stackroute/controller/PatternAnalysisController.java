package com.stackroute.controller;

import com.ibm.common.activitystreams.Activity;
import com.stackroute.domain.*;
import com.stackroute.repositories.BillingDetailsRepository;
import com.stackroute.repositories.IpAddressRepository;
import com.stackroute.service.BillingDetailService;
import com.stackroute.service.CardDetailService;
import com.stackroute.service.ItemService;
import com.stackroute.service.PatternAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.ibm.common.activitystreams.Makers.activity;
import static com.ibm.common.activitystreams.Makers.object;

@RestController
@RequestMapping("api/v1/")

public class PatternAnalysisController {

    private PatternAnalysisService patternAnalysisService;
    private ItemService itemService;
    private CardDetailService cardDetailService;
    private BillingDetailService billingDetailService;

    Logger logger = LoggerFactory.getLogger(PatternAnalysisController.class);

    @Autowired
    public PatternAnalysisController(PatternAnalysisService patternAnalysisService, ItemService itemService, CardDetailService cardDetailService, BillingDetailService billingDetailService) {
        this.patternAnalysisService = patternAnalysisService;
        this.itemService = itemService;
        this.cardDetailService = cardDetailService;
        this.billingDetailService = billingDetailService;
    }

    @GetMapping("item")
    public ResponseEntity<Iterable<TransactionDetails>> getByNo_Of_Items(String transaction_holder_name) {
        System.out.println(transaction_holder_name);
        System.out.println("inside controller method*******");
        Iterable<TransactionDetails> items = patternAnalysisService.findByno_of_items(transaction_holder_name);
        System.out.println(items);
        return new ResponseEntity<>(patternAnalysisService.findByno_of_items(transaction_holder_name), HttpStatus.OK);
    }

    @GetMapping("transactions/{transaction_holder_name}")
    public ResponseEntity<List<TransactionDetails>> getTransactions(@PathVariable("transaction_holder_name") String transaction_holder_name) {
        List<TransactionDetails> transactionDetails = patternAnalysisService.findTransactions(transaction_holder_name);
        System.out.println(transactionDetails);
        return new ResponseEntity<>(patternAnalysisService.findTransactions(transaction_holder_name), HttpStatus.OK);
    }

    @GetMapping("transaction/{transaction_holder_name}")
    public ResponseEntity<Iterable<IPAddress>> getByName(@PathVariable("transaction_holder_name") String transaction_holder_name) {
        Iterable<IPAddress> transactionDetails = patternAnalysisService.findByName(transaction_holder_name);
        System.out.println(transactionDetails);
        return new ResponseEntity<>(patternAnalysisService.findByName(transaction_holder_name), HttpStatus.OK);
    }

    @GetMapping("trans/{transaction_holder_name}")
    public ResponseEntity<Iterable<TransactionDetails>> getName(@PathVariable("transaction_holder_name") String transaction_holder_name) {
        Iterable<TransactionDetails> transactionDetails = patternAnalysisService.savedDetails(transaction_holder_name);
        System.out.println(transactionDetails);
        return new ResponseEntity<>(patternAnalysisService.savedDetails(transaction_holder_name), HttpStatus.OK);
    }

    @GetMapping("carddetails/{transaction_holder_name}")
    public ResponseEntity<Iterable<CardDetails>> getCardDetails(@PathVariable("transaction_holder_name") String transaction_holder_name) {
        Iterable<CardDetails> cardDetails = cardDetailService.findCardDetails(transaction_holder_name);
        System.out.println(cardDetails);
        return new ResponseEntity<>(cardDetailService.findCardDetails(transaction_holder_name), HttpStatus.OK);
    }

    @Autowired
    IpAddressRepository repository;
    BillingDetailsRepository billingDetailsRepository;

    @Autowired
    KafkaTemplate<String, Finance> kafkaTemplate;

    @Autowired
    KafkaTemplate<String,Email> fraudKafkaTemplate;


   @KafkaListener(topics = "UpstreamFinance",groupId = "json")
    public void saveTransactions(@RequestBody TransactionDetails transactionDetails) {
        List<TransactionDetails> list = new ArrayList();
       Location currentTransactionLocation = null;
        list = patternAnalysisService.findTransactions(transactionDetails.getTransaction_holder_name());
        String newTransactionTimeStamp = transactionDetails.getTimestamp();
        String holder = transactionDetails.getTransaction_holder_name();
        logger.info(holder);
        logger.info("New Transaction TimeStamp: " + newTransactionTimeStamp);
        String oldTransactionTimeStamp = "";
        logger.info("Transaction holder: " + transactionDetails.getTransaction_holder_name());
        logger.info("Old Transactions: " + patternAnalysisService.findTransactions(transactionDetails.getTransaction_holder_name()).toString());
        if (list != null && !list.isEmpty()) {
            for (TransactionDetails oldTransaction : list) {
                oldTransactionTimeStamp = oldTransaction.getTimestamp();
            }
        }
        String message = null;
        double distance = 0;
        long Timestampdiff = 0;
        if (!oldTransactionTimeStamp.isEmpty() || !oldTransactionTimeStamp.isBlank()) {
            logger.info("Old Transaction TimeStamp: " + oldTransactionTimeStamp);
            Timestampdiff = itemService.calculateDateDifference(oldTransactionTimeStamp, newTransactionTimeStamp);
            System.out.println("Difference between two timestamps is: " + Timestampdiff);




            Double latitude1 = null;
            Double longitude1 = null;
            Double latitude2 = null;
            Double longitude2 = null;


            for (IPAddress ipAddress : transactionDetails.getIpAddress()) {
                currentTransactionLocation = itemService.getLocation(ipAddress.getIpv4());
                latitude1 = currentTransactionLocation.getLatitude();
                longitude1 = currentTransactionLocation.getLongitude();
                System.out.println("Latitude of current location is" + latitude1);
                System.out.println("Longitude of current location is " + longitude1);
            }

            Collection<IPAddress> ipAddressList = patternAnalysisService.findByName(transactionDetails.transaction_holder_name);

            if (ipAddressList != null && !ipAddressList.isEmpty()) {
                for (IPAddress ipAddress : ipAddressList) {
                    Location previousLocation = itemService.getLocation(ipAddress.getIpv4());
                    latitude2 = previousLocation.getLatitude();
                    longitude2 = previousLocation.getLongitude();
                    System.out.println("Latitude of previous location is" + latitude2);
                    System.out.println("longitude of previous location is" + longitude2);
                }
            }


            distance = itemService.distance(latitude1, longitude1, latitude2, longitude2, "K");
            System.out.println("Distance between two locations is :" + distance);


            if ((Timestampdiff==0||Timestampdiff < 5) && distance > 1000) {
                System.out.println("fraudulent transaction");
                message = "fraudulent transaction";
                Email pojo1 = new Email(transactionDetails.transaction_holder_email,"fraudulent transaction occured","This mail is to inform you that the transaction occured couldnot be occured within this timestamp"+transactionDetails.timestamp+" difference from a far distance"+currentTransactionLocation.toString()+". Please report if transaction is not done by you.");
                fraudKafkaTemplate.send("Mailservice",pojo1);

            } else {
                System.out.println("genuine transaction");
                message = "genuine transaction";
            }
        }

        TransactionDetails obj = transactionDetails;
        System.out.println(obj);
        Activity activity = activity().object(String.valueOf(obj)).get();
        System.out.println(activity);
        Finance pojo = new Finance(UUID.randomUUID().toString(),"finance", "post", new ObjectTypes("FinanceMessage", String.valueOf(obj)), distance, Timestampdiff, message);
        kafkaTemplate.send("finance", pojo);
    }

    @PostMapping("Ipaddress")
    public ResponseEntity<IPAddress> saveIpAddress(@RequestBody IPAddress ipAddress) {
        System.out.println(ipAddress);
        repository.createNode(ipAddress.getId(), ipAddress.getIpv4(), ipAddress.getIpv6());
        return new ResponseEntity<IPAddress>(new IPAddress(), HttpStatus.OK);
    }

    @PostMapping("card")
    public ResponseEntity<CardDetails> saveCardDetails(@RequestBody CardDetails cardDetails) {
        System.out.println(cardDetails);
        cardDetailService.cardDetailNode(cardDetails);
        return new ResponseEntity<CardDetails>(cardDetailService.cardDetailNode(cardDetails), HttpStatus.OK);

    }

    @PostMapping("billing")
    public ResponseEntity<BillingDetails> saveBillingDetails(@RequestBody BillingDetails billingDetails) {
        System.out.println(billingDetails);
        billingDetailService.billingDetailNode(billingDetails);
        return new ResponseEntity<BillingDetails>(new BillingDetails(), HttpStatus.OK);

    }
}
