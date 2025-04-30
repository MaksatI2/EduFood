package edu.food.edufood.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_dishes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dishes dish;

    @Column(nullable = false)
    private Integer quantity;
}