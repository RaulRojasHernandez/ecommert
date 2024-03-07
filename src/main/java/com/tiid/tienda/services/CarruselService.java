package com.tiid.tienda.services;

import com.tiid.tienda.entities.Carrusel;
import com.tiid.tienda.requests.CarruselRequest;
import com.tiid.tienda.responses.CarruselResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.tiid.tienda.Utilities.AmazonUtils;

import java.util.*;

@Service
public class CarruselService {

    @Autowired
    private AmazonUtils amazonUtils;

    public CarruselResponse
    createCarrusel(CarruselRequest request, MultipartFile img){
        String carruselimg = request.getName()+"-"+"ImagenCarrusel";

        Carrusel carrusel = new Carrusel();
        carrusel.setName(request.getName());
        carrusel.setActive(true);

        amazonUtils.putObject(carruselimg,img);
        carrusel.setImg(carruselimg);

        Carrusel carruselSave=carrusel;

        return new CarruselResponse(carruselSave);
    }

}
