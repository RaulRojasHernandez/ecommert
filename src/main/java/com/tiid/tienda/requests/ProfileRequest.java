package com.tiid.tienda.requests;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {


    private String name;
    private boolean createProduct;
    private boolean editProduct;
    private boolean deleteProduct;
    private boolean editInventory;


}
