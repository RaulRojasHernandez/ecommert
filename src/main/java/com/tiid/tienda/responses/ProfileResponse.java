package com.tiid.tienda.responses;

import com.tiid.tienda.entities.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private Long id;
    private String name;
    private boolean createProduct;
    private boolean editProduct;
    private boolean deleteProduct;
    private boolean editInventory;

    public ProfileResponse(Profile profile){
        this.id = profile.getId();
        this.name = profile.getName();
        this.createProduct = profile.isCreateProduct();
        this.editProduct = profile.isEditProduct();
        this.deleteProduct = profile.isDeleteProduct();
        this.editInventory = profile.isEditInventory();
    }
}
