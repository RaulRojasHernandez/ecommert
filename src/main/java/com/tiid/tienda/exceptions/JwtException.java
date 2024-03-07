package com.tiid.tienda.exceptions;

public class JwtException extends RuntimeException{


    public JwtException(){
        super("JWT not valid or it's expired");
    }

}
