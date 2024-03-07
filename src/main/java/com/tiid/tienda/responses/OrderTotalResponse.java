package com.tiid.tienda.responses;

import com.tiid.tienda.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDate;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderTotalResponse {
    private Long id;

    private LocalDate dateCreated;

    private LocalDate dateShipped;

    private String status;

    private String paypalBougthId;

    private OrderDetailsResponse details;

    private ShippingResponse shippingResponse;

    public OrderTotalResponse(Order order){
        this.id = order.getId();
        this.dateCreated = order.getDateCreated();
        this.dateShipped = order.getDateShipped();
        this.status = order.getStatus();
        this.paypalBougthId = order.getPaypalBougthId();
        this.details = new OrderDetailsResponse(order.getOrderDetails());
        this.shippingResponse = new ShippingResponse(order.getShipping());

    }



}
