package com.stackroute.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class IPAddress {
    private String ipv4;
    private String ipv6;
    private String id;
}
