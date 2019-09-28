package com.stackroute.domain;

import lombok.*;
import org.aspectj.asm.internal.Relationship;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionDetails {

    public String transaction_id;
    public String transaction_holder_name;
    public String timestamp;
    public String transaction_holder_email;
    public String transaction_holder_phn_no;
    public String amount;
    private List<ShippingDetails> shippingDetails=new ArrayList<>();
    private List<BillingDetails> billingDetails=new ArrayList<>();
    private List<Item> item= new ArrayList<>();
    private List<CardDetails> cardDetails=new ArrayList<>();
    private List<IPAddress> ipAddress = new ArrayList<>();
}
