package com.hoffmann_g.order_service.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hoffmann_g.order_service.entities.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_table")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private Long totalAmount;
    private Long shipmentCost;
    private String paymentType;
    private LocalDateTime createdAt;
    
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

}
