package com.tiid.tienda.controllers;

import com.tiid.tienda.requests.SoldProductRequest;
import com.tiid.tienda.responses.OrderDetailsResponse;
import com.tiid.tienda.services.JwtService;
import com.tiid.tienda.services.OrderDetailsService;
import com.tiid.tienda.services.SoldProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order/details")
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;
    private final JwtService jwtService;

    @Autowired
    public OrderDetailsController(OrderDetailsService orderDetailsService, JwtService jwtService){
        this.orderDetailsService = orderDetailsService;
        this.jwtService = jwtService;

    }


    @GetMapping("/get")
    public ResponseEntity<OrderDetailsResponse> getDetailFromAOrder(@RequestHeader("Authorization") String jwt, @RequestParam Long id){
        String token = jwtService.cleanToken(jwt);
        String email = jwtService.getUserFromToken(token);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderDetailsService.getOrderDetailsResponse(email,id));

    }

//    @GetMapping("/order/pending")
//    public ResponseEntity<OrderDetailsResponse>getOrderPending(){
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(orderDetailsService.getOrderPending());
//    }




}
