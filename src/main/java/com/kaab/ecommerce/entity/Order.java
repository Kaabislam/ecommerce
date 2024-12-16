package com.kaab.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserInfo user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items;

    private String shippingAddress;
    private Double totalAmount;
    private String status; // CREATED, SHIPPED, DELIVERED

    @CreationTimestamp
    private LocalDateTime createdAt;

}
