package com.tiid.tienda.services;

import com.tiid.tienda.entities.Product;
import com.tiid.tienda.exceptions.GeneralExpection;
import com.tiid.tienda.exceptions.ResourceNotFoundExecption;
import com.tiid.tienda.repository.ProductRepository;
import com.tiid.tienda.responses.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final ProductRepository productRepository;


    @Autowired
    public InventoryService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }


    public ProductResponse editInventory(Long id, int quantity){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExecption("Product", "id", Long.toString(id)));
        if(quantity < 0){
            throw  new GeneralExpection("No negative numbers");
        }
        product.setQuantity(quantity);
        productRepository.save(product);
        return new ProductResponse(product);

    }


}
