package com.tiid.tienda.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


public class RecoveryPasswordHTML {


    private final String password;
    private final String name;


    public RecoveryPasswordHTML(String password, String name){
        this.password = password;
        this.name = name;
    }



    public String getHTML(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Tu nueva de Contraseña</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div style=\"text-align: center; background-color: #f5f5f5; padding: 20px;\">\n" +
                "        <h1>Recuperación de Contraseña</h1>\n" +
                "        <p>Hola, " + name + "</p>\n" + // Aquí se agrega el nombre
                "        <p>Parece que has olvidado tu contraseña. No te preocupes, estamos aquí para ayudarte.</p>\n" +
                "        <p>Esta es tu nueva contraseña:" + password  + " </p>\n" +
                "        <p>Tu Equipo de Soporte</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";


    }
}
