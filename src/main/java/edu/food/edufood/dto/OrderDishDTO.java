package edu.food.edufood.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDishDTO {
    private Long orderId;
    private Long dishId;
    private Integer quantity;
}
