package edu.food.edufood.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private List<OrderDishDTO> orderDishes;
}
