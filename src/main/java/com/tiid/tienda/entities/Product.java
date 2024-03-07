package com.tiid.tienda.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "priceWithDiscount")
    private Double priceWithDiscount;

    @Column(name = "quantity")
    private int quantity;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(mappedBy = "products")
    private List<Discount> discounts = new ArrayList<>();

    @Column(name = "img1", nullable = false)
    private String img1;

    @Column(name = "img2", nullable = true)
    private String img2;

    @Column(name = "img3", nullable = true)
    private String img3;

    @Column(name = "img4", nullable = true)
    private String img4;

    @Column(name = "vid", nullable = true)
    private String vid;

    @Column(name = "lowstock", nullable = false)
    private int lowstock;

    @OneToMany(mappedBy = "product")
    private List<SoldProducts> soldProductsList;





}
