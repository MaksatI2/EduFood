package edu.food.edufood.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {
    private Long id;
    private String sessionId;
    private Long dishId;
    private Long userId;
    private Integer quantity;
}
