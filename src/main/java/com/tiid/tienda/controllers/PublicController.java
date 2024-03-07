package com.tiid.tienda.controllers;

import com.tiid.tienda.entities.Category;
import com.tiid.tienda.entities.Discount;
import com.tiid.tienda.entities.Product;
import com.tiid.tienda.responses.CategoryResponse;
import com.tiid.tienda.responses.DiscountResponse;
import com.tiid.tienda.responses.ProductResponse;
import com.tiid.tienda.responses.SearchGlobalResponse;
import com.tiid.tienda.services.CategoryService;
import com.tiid.tienda.services.DiscountService;
import com.tiid.tienda.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final ProductService productService;
    private final DiscountService discountService;
    private final CategoryService categoryService;


    @Autowired
    public PublicController(ProductService productService, DiscountService discountService, CategoryService categoryService){
        this.categoryService = categoryService;
        this.discountService = discountService;
        this.productService = productService;
    }

    @GetMapping("/search")
    public ResponseEntity<SearchGlobalResponse> searchGlobal(@RequestParam String nameToSearch){

        List<ProductResponse> productList = productService.searchProduct(nameToSearch);
        List<CategoryResponse> categoryList = categoryService.searchCategories(nameToSearch);
        List<DiscountResponse> discountList = discountService.searchDiscount(nameToSearch);

        SearchGlobalResponse searchGlobalResponse = new SearchGlobalResponse();
        searchGlobalResponse.setCategoryList(categoryList);
        searchGlobalResponse.setProductList(productList);
        searchGlobalResponse.setDiscountList(discountList);


        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(searchGlobalResponse);

    }



}
