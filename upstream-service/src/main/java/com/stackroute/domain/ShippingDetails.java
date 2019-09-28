package com.stackroute.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShippingDetails {
    private String shipping_id;
    private String shipping_address;
}
