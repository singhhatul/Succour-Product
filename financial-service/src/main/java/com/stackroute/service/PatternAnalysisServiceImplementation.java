package com.stackroute.service;


import com.stackroute.domain.IPAddress;
import com.stackroute.domain.TransactionDetails;
import com.stackroute.repositories.PatternAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.List;

@Service
public class PatternAnalysisServiceImplementation implements PatternAnalysisService {
    private PatternAnalysis patternAnalysis;

    @Autowired
    public PatternAnalysisServiceImplementation(PatternAnalysis patternAnalysis) {
        this.patternAnalysis = patternAnalysis;
    }

    @Override
    public Collection<TransactionDetails> findByno_of_items(String transaction_holder_name) {
        System.out.println("inside service method********" + patternAnalysis.findByno_of_items(transaction_holder_name));
        return patternAnalysis.findByno_of_items(transaction_holder_name);
    }

    @Override
    public Collection<IPAddress> findByName(String transaction_holder_name){
         Collection<IPAddress> ipAddress=patternAnalysis.findByName(transaction_holder_name);
         return ipAddress;
    }
//    @Autowired(required = true)
//    KafkaTemplate<String,TransactionDetails> kafkaTemplate;

    @Override
//    @KafkaListener(topics = "financer",groupId = "json",containerFactory = "kafkaListener")

    public List<TransactionDetails> findTransactions(String transaction_holder_name){
        return patternAnalysis.findTransactions(transaction_holder_name);
        }



    @Override
    public TransactionDetails saveTransaction(TransactionDetails transactionDetails) {
      return patternAnalysis.save(transactionDetails);
    }

    @Override
    public List<TransactionDetails> savedDetails(String transaction_holder_name){
        return patternAnalysis.savedDetails(transaction_holder_name);
    }

}
