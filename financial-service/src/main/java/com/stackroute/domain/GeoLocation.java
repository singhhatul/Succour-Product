package com.stackroute.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeoLocation {
    private Double latitude;
    private Double longitude;
}
