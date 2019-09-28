package com.stackroute.service;

import com.stackroute.domain.BillingDetails;
import com.stackroute.repositories.BillingDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillingDetailServiceImplementation implements BillingDetailService {

    @Autowired
    BillingDetailsRepository billingDetailsRepository;

    @Override
    public BillingDetails billingDetailNode(BillingDetails billingDetails) {
        return billingDetailsRepository.save(billingDetails);
    }
}
