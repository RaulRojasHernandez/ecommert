package com.tiid.tienda.responses;


import com.tiid.tienda.entities.SoldProducts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoldProductsResponse {

    String name;
    Double price;
    Double priceWithDiscount;
    int quantity;
    String img1;

    public SoldProductsResponse(SoldProducts soldProducts){
        this.name = soldProducts.getProduct().getName();
        this.price = soldProducts.getPrice();
        this.priceWithDiscount = soldProducts.getPriceWithDiscount();
        this.quantity = soldProducts.getQuantity();
        this.img1 = soldProducts.getProduct().getImg1();

    }


}
