package com.tiid.tienda.controllers;

import com.tiid.tienda.entities.Product;
import com.tiid.tienda.requests.ProductRequest;
import com.tiid.tienda.responses.ProductIdRequest;
import com.tiid.tienda.responses.ProductResponse;
import com.tiid.tienda.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.google.gson.Gson;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private Gson gson;


    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductIdRequest>> getAllProducts(@RequestParam(defaultValue = "0")int page){
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.getAllProducts(PageRequest.of(page, 10)));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductIdRequest> getProductById(@PathVariable long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.getProductById(id));

    }





    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(@RequestParam("request") String requestJson,
                                                         @RequestParam(name = "photo1") MultipartFile photo1,
                                                         @RequestParam(name = "photo2", required = false) MultipartFile photo2,
                                                         @RequestParam(name = "photo3", required = false) MultipartFile photo3,
                                                         @RequestParam(name = "photo4", required = false) MultipartFile photo4,
                                                         @RequestParam(name = "video", required = false) MultipartFile video) {
        ProductRequest request = gson.fromJson(requestJson, ProductRequest.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.createProduct(request,photo1,photo2,photo3,photo4,video));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
       productService.deleteProduct(id);


       return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Deleted");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ProductResponse> editProduct(@RequestParam("request") String requestJson, @PathVariable Long id,
                                                       @RequestParam(name = "photo1") MultipartFile photo1,
                                                       @RequestParam(name = "photo2", required = false) MultipartFile photo2,
                                                       @RequestParam(name = "photo3", required = false) MultipartFile photo3,
                                                       @RequestParam(name = "photo4", required = false) MultipartFile photo4,
                                                       @RequestParam(name = "video", required = false) MultipartFile video){
        ProductRequest request = gson.fromJson(requestJson, ProductRequest.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.editProduct(request, id, photo1,photo2,photo3,photo4,video));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String nameToSearch){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.searchProduct(nameToSearch));

    }


}
