package com.tiid.tienda.responses;


import com.tiid.tienda.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class DashboardOrdersResponse {
    private Long id;
    private Double total;
    private LocalDate dateCreated;
    private String status;

    public DashboardOrdersResponse(Order order){
        this.id = order.getId();
        this.dateCreated = order.getDateCreated();
        this.status = order.getStatus();
        this.total = order.getOrderDetails().getTotal();
    }

}
