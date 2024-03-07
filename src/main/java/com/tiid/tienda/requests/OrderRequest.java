package com.tiid.tienda.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    String emailUser;
    String paypalBougthId;
    Long shippingId;
    List<SoldProductRequest> soldProductRequests;
}
