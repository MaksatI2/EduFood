package edu.food.edufood.service.impl;

import edu.food.edufood.model.Dishes;
import edu.food.edufood.repository.DishesRepository;
import edu.food.edufood.service.DishesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishesServiceImpl implements DishesService {
    private final DishesRepository dishRepository;

    @Override
    public List<Dishes> getAllDishes() {
        return dishRepository.findAll();
    }

    @Override
    public List<Dishes> getDishesByRestaurantId(Long restaurantId) {
        return dishRepository.findByRestaurantId(restaurantId);
    }
}