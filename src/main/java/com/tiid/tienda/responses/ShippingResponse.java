package com.tiid.tienda.responses;

import com.tiid.tienda.entities.Shipping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingResponse {

    Long id;
    String emailUser;
    String name;
    String state;
    String city;
    String street;
    String number;
    String internalNumber;
    String cologne;
    String referencesHouse;
    int cp;

    public ShippingResponse(Shipping shipping){
        this.id = shipping.getId();
        this.emailUser = shipping.getUser().getEmail();
        this.name = shipping.getName();
        this.state = shipping.getState();
        this.city = shipping.getCity();
        this.street = shipping.getStreet();
        this.number = shipping.getNumber();
        this.internalNumber = shipping.getInternalNumber();
        this.cologne = shipping.getCologne();
        this.referencesHouse = shipping.getReferencesHouse();
        this.cp = shipping.getCp();
    }

}
