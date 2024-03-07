package com.tiid.tienda.repository;

import com.tiid.tienda.entities.Discount;
import com.tiid.tienda.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    List<Discount> findByNameContaining(String nameToSearch);

    @Query("SELECT d FROM Discount d WHERE LOWER(d.name) LIKE %:name%")
    List<Discount> findDiscount(@Param("name") String name);
    List<Discount> findByEndDateBetweenAndActiveIsTrue(LocalDate start, LocalDate end);

}
