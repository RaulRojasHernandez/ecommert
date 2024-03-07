package com.tiid.tienda.responses;

import com.tiid.tienda.entities.Category;
import com.tiid.tienda.entities.Discount;
import com.tiid.tienda.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DiscountResponse {
    private Long id;
    private String name;
    private String description;
    private Double percentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<ProductDiscountResponse> products;
    private List<CategoryDiscountResponse> categories;
    private boolean isActive;


    public DiscountResponse(Discount discount){
        this.id = discount.getId();
        this.name = discount.getName();
        this.description = discount.getDescription();
        this.percentage = discount.getPercentage();
        this.startDate = discount.getStartDate();
        this.endDate = discount.getEndDate();
        this.products = createProductsResponse(discount.getProducts());
        this.categories = createCategoriesResponse(discount.getCategories());
        this.isActive = discount.isActive();
    }

    private List<ProductDiscountResponse> createProductsResponse(List<Product> products){
        List<ProductDiscountResponse> responses = new ArrayList<>();

        for(Product product: products){
            ProductDiscountResponse response = new ProductDiscountResponse(product);
            responses.add(response);
        }
        return responses;
    }

    private List<CategoryDiscountResponse> createCategoriesResponse(List<Category> categories){
        List<CategoryDiscountResponse> responses = new ArrayList<>();

        for(Category category: categories){
            CategoryDiscountResponse response = new CategoryDiscountResponse(category);
            responses.add(response);
        }
        return responses;
    }

}
