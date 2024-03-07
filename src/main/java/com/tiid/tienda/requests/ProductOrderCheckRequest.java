package com.tiid.tienda.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderCheckRequest {

    Long id;
    Integer quantity;
    Double price;

}
