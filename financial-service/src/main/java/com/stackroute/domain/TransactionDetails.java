package com.stackroute.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@NodeEntity(label = "Transaction")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id",scope=TransactionDetails.class)

public class TransactionDetails {

    @Id
    public String transaction_id;
    public String transaction_holder_name;
    public String timestamp;
    public String transaction_holder_email;
    public String transaction_holder_phn_no;
    public String amount;
    @Relationship(type = "is_shipping_address",direction = Relationship.OUTGOING)
    private List<ShippingDetails> shippingDetails=new ArrayList<>();
    @Relationship(type = "is_billing_address",direction = Relationship.OUTGOING)
    private List<BillingDetails> billingDetails=new ArrayList<>();
    @Relationship(type = "is_consists_of",direction = Relationship.INCOMING)
    private List<Item> item= new ArrayList<>();
    @Relationship(type = "has_a_transaction",direction = Relationship.INCOMING)
    private List<CardDetails> cardDetails=new ArrayList<>();
    @Relationship(type = "is_used_for",direction = Relationship.INCOMING)
    private List<IPAddress> ipAddress = new ArrayList<>();
}
