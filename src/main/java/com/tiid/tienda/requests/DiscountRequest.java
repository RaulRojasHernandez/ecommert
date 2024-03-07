package com.tiid.tienda.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountRequest {
    String name;
    String description;
    double percentage;
    LocalDate startDate;
    LocalDate endDate;
    long[] products_ids;
    long[] categories_ids;
    MultipartFile img;
    MultipartFile vid;
}
