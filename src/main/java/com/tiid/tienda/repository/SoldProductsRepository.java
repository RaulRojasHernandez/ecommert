package com.tiid.tienda.repository;

import com.tiid.tienda.entities.Category;
import com.tiid.tienda.entities.Product;
import com.tiid.tienda.entities.SoldProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SoldProductsRepository extends JpaRepository<SoldProducts, Long> {

    // Cambia el tipo de retorno a Product o Object[]
    @Query("SELECT sp.product, SUM(sp.quantity) AS totalQuantity " +
            "FROM SoldProducts sp " +
            "WHERE sp.orderDetails.order.dateCreated BETWEEN :startDate AND :endDate " +
            "GROUP BY sp.product " +
            "ORDER BY totalQuantity DESC")
    Product findMostSoldProduct(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);



    @Query("SELECT SUM(sp.price * sp.quantity) " +
            "FROM SoldProducts sp " +
            "WHERE sp.orderDetails.order.dateCreated BETWEEN :startDate AND :endDate")
    Double getTotalSalesAmount(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query("SELECT c, SUM(sp.quantity) AS totalQuantity " +
            "FROM SoldProducts sp " +
            "JOIN sp.product p " +
            "JOIN p.categories c " +
            "WHERE sp.orderDetails.order.dateCreated BETWEEN :startDate AND :endDate " +
            "GROUP BY c.id " +
            "ORDER BY totalQuantity ASC")
    List<Category> findTop10SellingCategories(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);





    @Query("SELECT COUNT(sp) " +
            "FROM SoldProducts sp " +
            "WHERE sp.orderDetails.order.dateCreated BETWEEN :startDate AND :endDate " +
            "GROUP BY sp.orderDetails.id")
    Integer countSalesByOrderDetails(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);



    @Query("SELECT p, SUM(sp.quantity) AS totalQuantity " +
            "FROM SoldProducts sp " +
            "JOIN sp.product p " +
            "WHERE sp.orderDetails.order.dateCreated BETWEEN :startDate AND :endDate " +
            "GROUP BY p.id " +
            "ORDER BY totalQuantity DESC")
    List<Product> findTop10SellingProducts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query("SELECT p, SUM(sp.quantity) AS totalQuantity " +
            "FROM SoldProducts sp " +
            "JOIN sp.product p " +
            "WHERE sp.orderDetails.order.dateCreated BETWEEN :startDate AND :endDate " +
            "GROUP BY p.id " +
            "ORDER BY totalQuantity ASC") // Cambiado a ASC para ordenar de menor a mayor
    List<Product> findBottom10SellingProducts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

//    @Query("SELECT '*' FROM SoldProducts")
//    List<SoldProducts> findOP();


}
