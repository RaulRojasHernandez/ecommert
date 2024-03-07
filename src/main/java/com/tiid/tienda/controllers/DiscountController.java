package com.tiid.tienda.controllers;

import com.tiid.tienda.entities.Discount;
import com.tiid.tienda.requests.DiscountRequest;
import com.tiid.tienda.responses.DiscountResponse;
import com.tiid.tienda.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discount")
public class DiscountController {

    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService){
        this.discountService = discountService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DiscountResponse>> getAllDiscounts(@RequestParam(defaultValue = "0")int page){
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(discountService.getAllDiscounts(PageRequest.of(page, 10)));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<DiscountResponse> getDiscountById(@PathVariable long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(discountService.getDiscountById(id));

    }

    @PostMapping("/create")
    public ResponseEntity<DiscountResponse> createDiscount(@RequestBody DiscountRequest request){

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(discountService.createDiscount(request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDiscount(@PathVariable Long id){
        discountService.deleteDiscount(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Deleted");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Discount> editDiscount(@RequestBody DiscountRequest request, @PathVariable Long id){
       return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(discountService.editDiscount(request, id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<DiscountResponse>> searchDiscount(@RequestParam String nameToSearch){
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(discountService.searchDiscount(nameToSearch));
    }


}
