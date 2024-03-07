package com.tiid.tienda.services;

import com.tiid.tienda.entities.OrderDetails;
import com.tiid.tienda.entities.Product;
import com.tiid.tienda.entities.SoldProducts;
import com.tiid.tienda.exceptions.ResourceNotFoundExecption;
import com.tiid.tienda.repository.ProductRepository;
import com.tiid.tienda.repository.SoldProductsRepository;
import com.tiid.tienda.requests.SoldProductRequest;
import com.tiid.tienda.responses.SoldProductsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SoldProductService {

    private final SoldProductsRepository soldProductsRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SoldProductService(SoldProductsRepository soldProductsRepository, ProductRepository productRepository){
        this.soldProductsRepository = soldProductsRepository;
        this.productRepository = productRepository;
    }

    public void saveProductSold(OrderDetails orderDetails, List<SoldProductRequest> soldProducts){

        for(SoldProductRequest soldProductRequest: soldProducts){
            Product product = productRepository.findById(soldProductRequest.getProductID()).orElseThrow(() -> new ResourceNotFoundExecption("product", "id", Long.toString(soldProductRequest.getProductID())));
            SoldProducts soldP = new SoldProducts();
            soldP.setProduct(product);
            soldP.setPrice(product.getPrice());
            soldP.setQuantity(soldProductRequest.getQuantity());
            soldP.setOrderDetails(orderDetails);

            if(product.getPriceWithDiscount() == null){
                soldP.setPriceWithDiscount(0);
            }else{
                soldP.setPriceWithDiscount(product.getPriceWithDiscount());
            }


            soldProductsRepository.save(soldP);
        }

    }



}
