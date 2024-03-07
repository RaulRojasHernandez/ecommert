package com.tiid.tienda.exceptions;

public class ShippingError extends RuntimeException{

    public ShippingError(String email){
        super("It is not possible to save more than three addresses for this user: " + email);
    }
}
