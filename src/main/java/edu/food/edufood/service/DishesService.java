package edu.food.edufood.service;

import edu.food.edufood.dto.DishesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DishesService {

    Page<DishesDTO> getAllDishes(Pageable pageable);

    List<DishesDTO> getDishesByRestaurantId(Long restaurantId);
}