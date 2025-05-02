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
public class OrderHistoryDTO {
    private Long id;
    private LocalDateTime createdAt;
    private String formattedCreatedAt;
    private BigDecimal totalPrice;
    private List<OrderDishDetailsDTO> dishes;
}