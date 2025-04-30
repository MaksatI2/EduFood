package edu.food.edufood.service;

import edu.food.edufood.model.Dishes;
import java.util.List;

public interface DishesService {
    List<Dishes> getAllDishes();
    List<Dishes> getDishesByRestaurantId(Long restaurantId);
}