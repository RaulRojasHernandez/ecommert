package com.tiid.tienda.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class GraphicResponse {
    ProductResponse mostProductSold;
    Double totalSalesAmount;
    List<CategoryResponse> top10SellingCategories;
    List<ProductResponse> top10SellingProducts;
    List<ProductResponse> top10BottomProducts;
    Integer countSales;
    Integer totalEmployees;
    Integer totalBuyers;

    public GraphicResponse(ProductResponse mostProductSold, Double totalSalesAmount, List<CategoryResponse> top10SellingCategories, List<ProductResponse> top10SellingProducts, List<ProductResponse> top10BottomProducts, Integer countSales, Integer totalEmployees, Integer totalBuyers){

        this.mostProductSold = mostProductSold;
        this.totalSalesAmount = totalSalesAmount;
        this.top10SellingCategories = top10SellingCategories;
        this.top10SellingProducts = top10SellingProducts;
        this.top10BottomProducts = top10BottomProducts;
        this.countSales = countSales;
        this.totalEmployees = totalEmployees;
        this.totalBuyers = totalBuyers;

    }

}
