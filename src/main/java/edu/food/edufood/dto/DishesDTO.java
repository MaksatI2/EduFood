package edu.food.edufood.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishesDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private String photoUrl;
    private String description;
    private Integer weight;
    private Long restaurantId;
    private String restaurantName;
}
