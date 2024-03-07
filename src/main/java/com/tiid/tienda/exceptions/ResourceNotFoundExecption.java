package com.tiid.tienda.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundExecption extends RuntimeException{
    private static final long serialVesionUID = 1L;
    private String nameOfResource;
    private String nameOfField;
    private String valueOfField;


    public ResourceNotFoundExecption(String nameOfResource, String nameOfField, String valueOfField){
        super(String.format("%s Not found %s: '%s'", nameOfResource, nameOfField, valueOfField));
        this.nameOfResource = nameOfResource;
        this.nameOfField = nameOfField;
        this.valueOfField = valueOfField;



    }


    public String getNameOfResource() {
        return nameOfResource;
    }

    public void setNameOfResource(String nameOfResource) {
        this.nameOfResource = nameOfResource;
    }

    public String getNameOfField() {
        return nameOfField;
    }

    public void setNameOfField(String nameOfField) {
        this.nameOfField = nameOfField;
    }

    public String getValueOfField() {
        return valueOfField;
    }

    public void setValueOfField(String valueOfField) {
        this.valueOfField = valueOfField;
    }






}
