package com.tiid.tienda.responses;

import com.tiid.tienda.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductDiscountResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double priceWithDiscount;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String vid;

    public ProductDiscountResponse(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.priceWithDiscount = product.getPriceWithDiscount();
        this.img1 = product.getImg1();
        this.img2 = product.getImg2();
        this.img3 = product.getImg3();
        this.img4 = product.getImg4();
        this.vid = product.getVid();
    }



}
