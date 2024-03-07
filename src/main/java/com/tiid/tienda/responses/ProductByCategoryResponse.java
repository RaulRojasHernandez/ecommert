package com.tiid.tienda.responses;

import com.tiid.tienda.entities.Category;
import com.tiid.tienda.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProductByCategoryResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double priceWithDiscount;
//    private List<Long> category_id;


    public ProductByCategoryResponse(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.priceWithDiscount = product.getPriceWithDiscount();


    }

    private List<Long> createCategoriesIdsList(List<Category> categories){
        List<Long> idsCateries = new ArrayList<>();

        for(Category category: categories){
            idsCateries.add(category.getId());
        }

        return idsCateries;
    }




}
