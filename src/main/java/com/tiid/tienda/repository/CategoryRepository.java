package com.tiid.tienda.repository;

import com.tiid.tienda.entities.Category;
import com.tiid.tienda.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameContaining(String nameToSearch);

    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE %:name%")
    List<Category> findCategory(@Param("name") String name);



}
