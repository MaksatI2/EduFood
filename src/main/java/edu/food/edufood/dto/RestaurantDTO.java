package edu.food.edufood.dto;

import edu.food.edufood.model.Dishes;
import edu.food.edufood.model.Order;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDTO {
    private Long id;
    private String name;
    private String photoUrl;
    private Set<Dishes> dishes;
    private Set<Order> orders;
}
