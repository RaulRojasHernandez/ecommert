package com.tiid.tienda.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    String name;
    String description;
    double cost;
    double price;
    int quantity;
    MultipartFile img1;
    MultipartFile img2;
    MultipartFile img3;
    MultipartFile img4;
    MultipartFile vid;
    Long category_id;
    Long discount_id;
    int lowstock;
}
