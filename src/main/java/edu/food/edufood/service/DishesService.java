package edu.food.edufood.service;

import edu.food.edufood.model.Dishes;
import edu.food.edufood.dto.DishesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DishesService {
    Page<DishesDTO> getAllDishes(Pageable pageable);
    Dishes getDishById(Long dishId);
    Page<DishesDTO> getDishesByRestaurantId(Long restaurantId, Pageable pageable);
    List<DishesDTO> getDishesByIds(List<Long> dishIds);
    Map<Long, Dishes> getDishesMapByIds(List<Long> ids);
}