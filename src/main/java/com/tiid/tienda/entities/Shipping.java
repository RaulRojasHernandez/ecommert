package com.tiid.tienda.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipping")
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToOne
//    @JoinColumn(name = "order_id") // Nombre de la columna en la tabla 'order_details'
//    private Order order;

    @OneToOne(mappedBy = "shipping", cascade = CascadeType.ALL)
    private Order order;


//    @Convert(converter = DataEncryptor.class)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "internalNumber", nullable = false)
    private String internalNumber;

    @Column(name = "cologne", nullable = false)
    private String cologne;

    @Column(name = "references_house", nullable = false)
    private String referencesHouse;

    @Column(name = "cp", nullable = false)
    private int cp;

    @Column(name = "active", nullable = false)
    private boolean isActive;

}
