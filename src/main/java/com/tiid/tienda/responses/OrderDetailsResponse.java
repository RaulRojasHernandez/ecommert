package com.tiid.tienda.responses;

import com.tiid.tienda.entities.OrderDetails;
import com.tiid.tienda.entities.SoldProducts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsResponse {
    Long idOrder;
    Double total;
    List<SoldProductsResponse> soldProductsResponse;


    public OrderDetailsResponse(OrderDetails orderDetails){
        this.idOrder = orderDetails.getId();
        this.total = orderDetails.getTotal();
        this.soldProductsResponse = createListOfProductsResponse(orderDetails.getSoldProducts());
    }

    private List<SoldProductsResponse> createListOfProductsResponse(List<SoldProducts> soldProducts){
        List<SoldProductsResponse> responseList = new ArrayList<>();

        for(SoldProducts sold: soldProducts){
            SoldProductsResponse response = new SoldProductsResponse(sold);
            responseList.add(response);
        }

        return responseList;
    }


}
