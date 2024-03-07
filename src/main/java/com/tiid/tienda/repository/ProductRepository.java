package com.tiid.tienda.repository;

import com.tiid.tienda.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE %:name%")
    List<Product> findProduct(@Param("name") String name);

    @Query("SELECT '*' FROM Product")
    List<Product> findAll(@Param("name") String name);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :categorieId")
    List<Product> findByCategory_Id(@Param("categorieId") Long categorieId);



}
