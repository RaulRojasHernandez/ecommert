package com.tiid.tienda.controllers;

import com.tiid.tienda.entities.Product;
import com.tiid.tienda.responses.GraphicResponse;
import com.tiid.tienda.responses.SalesResponse;
import com.tiid.tienda.services.GraphicsService;
import com.tiid.tienda.services.SoldProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/graphics")
public class GraphicsController {

    private GraphicsService graphicsService;

    @Autowired
    public GraphicsController ( GraphicsService graphicsService){
        this.graphicsService = graphicsService;
    }

    @GetMapping(value = "/get/data")
    public ResponseEntity<GraphicResponse> getAllData(@RequestParam(defaultValue = "")LocalDate startDate, @RequestParam(defaultValue = "")LocalDate endDate){

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(graphicsService.getAllData(startDate, endDate));
    }

    @GetMapping("/get/by-month")
    public ResponseEntity<SalesResponse> getSalesByMonth(@RequestParam(defaultValue = "")LocalDate startDate, @RequestParam(defaultValue = "")LocalDate endDate) {
        SalesResponse salesByMonth = graphicsService.getSalesByMonth(startDate, endDate);
        return ResponseEntity.ok(salesByMonth);
    }



}
