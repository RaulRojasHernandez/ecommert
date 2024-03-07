package com.tiid.tienda.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_details", uniqueConstraints = {@UniqueConstraint(columnNames = {"order_id"})})
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id") // Nombre de la columna en la tabla 'order_details'
    private Order order;

    @Column(name = "total", nullable = false)
    private double total;

    @OneToMany(mappedBy = "orderDetails", cascade = CascadeType.ALL)
    private List<SoldProducts> soldProducts;


}
