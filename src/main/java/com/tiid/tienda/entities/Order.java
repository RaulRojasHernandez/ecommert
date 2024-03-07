package com.tiid.tienda.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id") // Define la
    private User user;

    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @Column(name = "date_shipped")
    private LocalDate dateShipped;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "paypal_bougth_id", nullable = false)
    private String paypalBougthId;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderDetails orderDetails;

    @ManyToOne
    @JoinColumn(name = "shipping_id")
    private Shipping shipping;


    @Column(name = "address", nullable = false)
    private String address;

}
