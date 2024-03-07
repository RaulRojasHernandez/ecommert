package com.tiid.tienda.repository;

import com.tiid.tienda.entities.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {

    @Query("SELECT s from Shipping s where s.user.email = :emailUser and s.isActive = true")
    List<Shipping> findAllShippignsUser(@Param("emailUser")String emailUser);



}
