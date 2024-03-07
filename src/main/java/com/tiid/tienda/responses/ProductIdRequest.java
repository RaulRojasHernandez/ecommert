package com.tiid.tienda.responses;

import com.tiid.tienda.entities.Category;
import com.tiid.tienda.entities.Discount;
import com.tiid.tienda.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductIdRequest {

    private Long id;
    private String name;
    private String description;
    private double price;
    private double priceWithDiscount;
    private int quantity;
    private byte[] img1;
    private byte[] img2;
    private byte[] img3;
    private byte[] img4;
    private byte[] vid;
    private List<CategoryByProductResponse> categories;
    private List<DiscountCategoryOrProductResponse> discounts;
//    private int stock;
    private int lowstock;

    public ProductIdRequest(Product productIdRequest) {
        this.id = productIdRequest.getId();
        this.name = productIdRequest.getName();
        this.description = productIdRequest.getDescription();
        this.price = productIdRequest.getPrice();
        this.priceWithDiscount = productIdRequest.getPriceWithDiscount() == null ? 0 : productIdRequest.getPriceWithDiscount();
        this.quantity = productIdRequest.getQuantity();
        this.categories = createCategoriesResponse(productIdRequest.getCategories());
        this.discounts = createDiscountResponse(productIdRequest.getDiscounts());
//        this.stock = productIdRequest.getStock();
        this.lowstock = productIdRequest.getLowstock();
    }

    private List<CategoryByProductResponse> createCategoriesResponse(List<Category> categories) {
        List<CategoryByProductResponse> responses = new ArrayList<>();

        for (Category category : categories) {
            CategoryByProductResponse response = new CategoryByProductResponse(category);
            responses.add(response);
        }

        return responses;
    }


    private List<DiscountCategoryOrProductResponse> createDiscountResponse(List<Discount> discounts) {
        List<DiscountCategoryOrProductResponse> responses = new ArrayList<>();

        for (Discount discount : discounts) {
            DiscountCategoryOrProductResponse response = new DiscountCategoryOrProductResponse(discount);
            responses.add(response);
        }

        return responses;
    }
}

