package com.stackroute.service;

import com.stackroute.domain.TransactionDetails;
import com.stackroute.domain.IPAddress;

import java.util.Collection;
import java.util.List;


public interface PatternAnalysisService {

    Collection<TransactionDetails> findByno_of_items(String transaction_holder_name);

    Collection<IPAddress> findByName(String transaction_holder_name);

    TransactionDetails saveTransaction(TransactionDetails transactionDetails);

    List<TransactionDetails> findTransactions(String transaction_holder_name);

    List<TransactionDetails> savedDetails(String transaction_holder_name);

}
