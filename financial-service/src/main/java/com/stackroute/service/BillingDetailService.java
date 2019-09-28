package com.stackroute.service;

import com.stackroute.domain.BillingDetails;
import org.springframework.stereotype.Service;

@Service
public interface BillingDetailService {

    BillingDetails billingDetailNode(BillingDetails billingDetails);
}
