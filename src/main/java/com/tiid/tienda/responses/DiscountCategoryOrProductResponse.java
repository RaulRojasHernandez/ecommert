package com.tiid.tienda.responses;

import com.tiid.tienda.entities.Discount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class DiscountCategoryOrProductResponse {

    private Long id;
    private String name;
    private String description;
    private Double percentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private String img;
    private String vid;
    private boolean isActive;

    public DiscountCategoryOrProductResponse(Discount discount){
        this.id = discount.getId();
        this.name = discount.getName();
        this.description = discount.getDescription();
        this.percentage = discount.getPercentage();
        this.startDate = discount.getStartDate();
        this.endDate = discount.getEndDate();
        this.img = discount.getImg();
        this.vid = discount.getVid();
        this.isActive = discount.isActive();
    }


}
