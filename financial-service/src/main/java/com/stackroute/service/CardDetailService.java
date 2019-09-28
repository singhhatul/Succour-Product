package com.stackroute.service;

import com.stackroute.domain.CardDetails;

import java.util.List;

public interface CardDetailService {

    CardDetails cardDetailNode(CardDetails cardDetails);

    List<CardDetails> findCardDetails(String transaction_holder_name);
}
