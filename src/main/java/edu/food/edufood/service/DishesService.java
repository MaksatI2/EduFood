package edu.food.edufood.service;

import edu.food.edufood.dto.DishesDTO;
import edu.food.edufood.model.Dishes;

import java.util.List;
import java.util.Map;

public interface DishesService {
    List<DishesDTO> getAllDishes();
    List<DishesDTO> getDishesByRestaurantId(Long restaurantId);
    List<DishesDTO> getDishesByIds(List<Long> dishIds);
    Map<Long, Dishes> getDishesMapByIds(List<Long> ids);
}