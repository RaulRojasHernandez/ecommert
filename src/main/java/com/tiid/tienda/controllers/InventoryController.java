package com.tiid.tienda.controllers;

import com.tiid.tienda.requests.EditInventoryRequest;
import com.tiid.tienda.requests.ProductRequest;
import com.tiid.tienda.responses.ProductResponse;
import com.tiid.tienda.services.InventoryService;
import com.tiid.tienda.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
     public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }


    @PutMapping("/product")
    public ResponseEntity<ProductResponse> editInventoryProduct(@RequestBody EditInventoryRequest request){
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(inventoryService.editInventory(request.getId(), request.getQuantity()));
    }



}
