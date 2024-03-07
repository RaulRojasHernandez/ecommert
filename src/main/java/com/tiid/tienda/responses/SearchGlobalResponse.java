package com.tiid.tienda.responses;

import com.tiid.tienda.entities.Category;
import com.tiid.tienda.entities.Discount;
import com.tiid.tienda.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchGlobalResponse {
    List<ProductResponse> productList;
    List<CategoryResponse> categoryList;
    List<DiscountResponse> discountList;

}
