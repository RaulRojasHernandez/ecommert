package com.tiid.tienda.repository;

import com.tiid.tienda.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser_Email (String email);


    @Query("SELECT YEAR(o.dateCreated) AS year, MONTH(o.dateCreated) AS month, SUM(o.orderDetails.total) AS totalAmount " +
            "FROM Order o " +
            "WHERE o.dateCreated BETWEEN :startDate AND :endDate " +
            "GROUP BY YEAR(o.dateCreated), MONTH(o.dateCreated)")
    List<Map<String, Object>> getSalesByMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
