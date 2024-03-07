package com.tiid.tienda.responses;

import com.tiid.tienda.entities.Category;
import com.tiid.tienda.entities.Discount;
import com.tiid.tienda.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private List<DiscountCategoryOrProductResponse> discounts;
    private List<ProductByCategoryResponse> products;


    public CategoryResponse(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.discounts = createCategoriesResponse(category.getDiscounts());
        this.products = createProductsResponse(category.getProducts());
    }


    private List<ProductByCategoryResponse> createProductsResponse(List<Product> productList){
        List<ProductByCategoryResponse> responses = new ArrayList<>();
        for(Product product: productList){
            ProductByCategoryResponse response = new ProductByCategoryResponse(product);
            responses.add(response);
        }
        return  responses;
    }

    private List<DiscountCategoryOrProductResponse> createCategoriesResponse(List<Discount> discountsList){
        List<DiscountCategoryOrProductResponse> responses = new ArrayList<>();
        for(Discount discount: discountsList){
            DiscountCategoryOrProductResponse response = new DiscountCategoryOrProductResponse(discount);
            responses.add(response);
        }
        return  responses;


    }


}
