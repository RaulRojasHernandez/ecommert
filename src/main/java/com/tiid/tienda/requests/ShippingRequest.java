package com.tiid.tienda.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingRequest {

    String name;
    String state;
    String city;
    String street;
    String number;
    String internalNumber;
    String cologne;
    String referencesHouse;
    int cp;
}
