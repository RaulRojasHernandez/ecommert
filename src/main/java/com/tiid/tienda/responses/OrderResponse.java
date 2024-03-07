package com.tiid.tienda.responses;

import com.tiid.tienda.entities.OrderDetails;
import com.tiid.tienda.entities.Shipping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    Long id;
    String emailUser;
    LocalDate dateCreated;
    LocalDate dateShipped;
    String status;
    OrderDetailsResponse orderDetailsResponse;
    ShippingResponse shippingResponse;

}
