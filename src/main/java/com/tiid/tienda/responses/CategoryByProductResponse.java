package com.tiid.tienda.responses;

import com.tiid.tienda.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryByProductResponse {

    private Long id;
    private String name;
    private String description;
    private String img;

    public CategoryByProductResponse(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.img = category.getImg();
    }

}
