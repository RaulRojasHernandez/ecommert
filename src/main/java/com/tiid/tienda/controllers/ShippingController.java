package com.tiid.tienda.controllers;

import com.tiid.tienda.entities.Shipping;
import com.tiid.tienda.repository.ShippingRepository;
import com.tiid.tienda.requests.ShippingRequest;
import com.tiid.tienda.responses.ShippingResponse;
import com.tiid.tienda.services.JwtService;
import com.tiid.tienda.services.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    private final ShippingService shippingService;
    private final JwtService jwtService;

    @Autowired
    public ShippingController(ShippingService shippingService, JwtService jwtService){
        this.shippingService = shippingService;
        this.jwtService = jwtService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<ShippingResponse>> getAllShippingUser(@RequestHeader("Authorization") String jwt){
        String token = jwtService.cleanToken(jwt);
        String email = jwtService.getUserFromToken(token);
        List<ShippingResponse> shippings = shippingService.getAllAdressUser(email);
        if(shippings.size() == 0){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(null);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(shippings);

    }

    @PostMapping("/create")
    public ResponseEntity<String>  createShipping (@RequestHeader("Authorization") String jwt, @RequestBody ShippingRequest request){
        String token = jwtService.cleanToken(jwt);
        String email = jwtService.getUserFromToken(token);
        shippingService.saveShippingByUser(request, email);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Created");




    }



}
