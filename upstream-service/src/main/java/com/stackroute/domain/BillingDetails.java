package com.stackroute.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillingDetails {
    private String billing_address;
    private String bill_id;
}
