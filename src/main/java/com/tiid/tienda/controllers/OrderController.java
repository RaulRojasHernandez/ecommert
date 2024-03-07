package com.tiid.tienda.controllers;

import com.tiid.tienda.entities.Order;
import com.tiid.tienda.requests.ItsOkOrderRequest;
import com.tiid.tienda.requests.OrderRequest;
import com.tiid.tienda.requests.ProductOrderCheckRequest;
import com.tiid.tienda.requests.SoldProductRequest;
import com.tiid.tienda.responses.OrderResponse;
import com.tiid.tienda.responses.ProductIdRequest;
import com.tiid.tienda.responses.SoldProductsResponse;
import com.tiid.tienda.services.JwtService;
import com.tiid.tienda.services.OrderService;
import com.tiid.tienda.services.SoldProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final JwtService jwtService;


    @Autowired
    public OrderController(OrderService orderService, JwtService jwtService){
        this.orderService = orderService;
        this.jwtService = jwtService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getOrders(@RequestHeader("Authorization") String jwt){
        String token = jwtService.cleanToken(jwt);
        String email = jwtService.getUserFromToken(token);

        List<OrderResponse> orderList = orderService.getAllOrdersUser(email);

        if (orderList.size() == 0){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(orderList);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderList);

    }


    @PostMapping("/its/ok")
    public ResponseEntity<Boolean> checkBeforeOrder(@RequestBody ItsOkOrderRequest request){
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderService.checkBeforeOrder(request));

    }


    @PostMapping("/create")
    public ResponseEntity<String> createResponse(@RequestBody OrderRequest request){
         orderService.createOrder(request);
         return ResponseEntity
                 .status(HttpStatus.CREATED)
                 .contentType(MediaType.APPLICATION_JSON)
                 .body("Created");
    }

//    @GetMapping("/order/pending")
//    public ResponseEntity<SoldProductsResponse>getOrderPending(){
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(SoldProductService.getOrderPending());
//    }






}
