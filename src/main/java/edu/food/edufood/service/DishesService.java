package edu.food.edufood.service;

import edu.food.edufood.dto.DishesDTO;
import java.util.List;

public interface DishesService {
    List<DishesDTO> getAllDishes();
    List<DishesDTO> getDishesByRestaurantId(Long restaurantId);
}