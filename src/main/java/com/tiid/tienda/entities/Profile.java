package com.tiid.tienda.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profiles", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "createProduct", nullable = false)
    private boolean createProduct;

    @Column(name = "editProduct", nullable = false)
    private boolean editProduct;

    @Column(name = "deleteProduct", nullable = false)
    private boolean deleteProduct;

    @Column(name = "editInventory", nullable = false)
    private boolean editInventory;

    @OneToMany(mappedBy = "profile")
    private List<User> users;





}
