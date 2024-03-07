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
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double priceWithDiscount;
    private int quantity;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String vid;
    private List<CategoryByProductResponse> categories;
    private List<DiscountCategoryOrProductResponse> discounts;
    int lowstock;


    public ProductResponse(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.priceWithDiscount = product.getPriceWithDiscount();
        this.quantity = product.getQuantity();
        this.img1 = product.getImg1();
        this.img2 = product.getImg2();
        this.img3 = product.getImg3();
        this.img4 = product.getImg4();
        this.vid = product.getVid();
        this.categories = createCategoriesResponse(product.getCategories());
        this.discounts = createDiscountResponse(product.getDiscounts());
        this.lowstock = product.getLowstock();
    }

    private List<CategoryByProductResponse> createCategoriesResponse(List<Category> categories){
        List<CategoryByProductResponse> responses = new ArrayList<>();

        for(Category category: categories){
            CategoryByProductResponse response = new CategoryByProductResponse(category);
            responses.add(response);
        }

        return responses;
    }


    private List<DiscountCategoryOrProductResponse> createDiscountResponse(List<Discount> discounts){
        List<DiscountCategoryOrProductResponse> responses = new ArrayList<>();

        for(Discount discount: discounts){
            DiscountCategoryOrProductResponse response = new DiscountCategoryOrProductResponse(discount);
            responses.add(response);
        }

        return responses;
    }




}
