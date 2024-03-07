package com.tiid.tienda.controllers;

import com.tiid.tienda.entities.Category;
import com.tiid.tienda.requests.CategoryRequest;
import com.tiid.tienda.responses.CategoryResponse;
import com.tiid.tienda.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class Categorycontroller {

    private final CategoryService categoryService;

    @Autowired
    public Categorycontroller(CategoryService categoryService){
        this.categoryService = categoryService;
    }


    @GetMapping(value = "/all")
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(@RequestParam(defaultValue = "0") int page){
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.getAllCategories(PageRequest.of(page, 10)));
    }

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.getCategoryById(id));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createCategory(@RequestBody CategoryRequest request){

        categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Created");
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteCategory (@PathVariable Long id){

        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Deleted");
    }

    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<CategoryResponse> editCategory (@RequestBody CategoryRequest request, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.editCategory(request, id));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<CategoryResponse>> searchCategoryByName(@RequestParam String nameToSearch){
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.searchCategories(nameToSearch));
    }

}


