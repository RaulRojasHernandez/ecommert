package com.tiid.tienda.Utilities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@Builder
@NoArgsConstructor
public class Utility {

    public double getPriceWithDiscount(double price, double discount){
        return price - (price * (discount / 100.0));
    }

    public String convertB64(String textToConvert){
        byte[] bytes = Base64.getEncoder().encode(textToConvert.getBytes());
        return new String(bytes);

    }

    public String deconvertB64(String textToDeconvert){
        byte[] bytes = Base64.getDecoder().decode(textToDeconvert);
        return new String(bytes);


    }


}
