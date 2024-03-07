package com.tiid.tienda.repository;

import com.tiid.tienda.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    @Query(value = "SELECT u FROM User u WHERE u.active = true AND u.role = 'BUYER' ")
    List<User> findActiveUsers();


    @Query(value = "SELECT u FROM User u WHERE u.active = true AND u.role = 'EMPLOYEE' ")
    List<User> findAllEmployeesActive();

    @Query(value = "SELECT u FROM User u WHERE u.role = 'EMPLOYEE' ")
    List<User> findAllEmployees();

    @Query(value = "SELECT u FROM User u WHERE u.role = 'ADMIN'")
    User hasAdmin();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = com.tiid.tienda.entities.Role.EMPLOYEE")
    int countEmployees();


    @Query("SELECT COUNT(u) FROM User u WHERE u.role = com.tiid.tienda.entities.Role.BUYER")
    int countBuyers();

    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE %:name% AND u.role='EMPLOYEE'")
    List<User> findbyname(@Param("name") String name);


}
