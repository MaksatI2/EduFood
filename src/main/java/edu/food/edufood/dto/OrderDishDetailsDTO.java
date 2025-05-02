package edu.food.edufood.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDishDetailsDTO {
    private String name;
    private String photoUrl;
    private BigDecimal price;
    private Integer quantity;
    private String restaurantName;
    private String restaurantPhotoUrl;
}