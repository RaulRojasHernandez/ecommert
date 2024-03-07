package com.tiid.tienda.repository;

import com.tiid.tienda.entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {


    @Query("SELECT od FROM OrderDetails od WHERE od.order.user.email = :emailUser AND od.order.id = :idOrder")
    OrderDetails findOrderDetailByEmailUserAndOrderId(@Param("emailUser") String emailUser, @Param("idOrder") Long idOrder);

    @Query("SELECT '*' FROM OrderDetails")
    OrderDetails findallOrderDetail();

}
