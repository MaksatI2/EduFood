package edu.food.edufood.service.impl;

import edu.food.edufood.dto.DishesDTO;
import edu.food.edufood.model.Dishes;
import edu.food.edufood.repository.DishesRepository;
import edu.food.edufood.service.DishesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishesServiceImpl implements DishesService {
    private final DishesRepository dishRepository;

    @Override
    public List<DishesDTO> getAllDishes() {
        return dishRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DishesDTO> getDishesByRestaurantId(Long restaurantId) {
        return dishRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DishesDTO convertToDTO(Dishes dish) {
        return DishesDTO.builder()
                .id(dish.getId())
                .name(dish.getName())
                .price(dish.getPrice())
                .photoUrl(dish.getPhotoUrl())
                .description(dish.getDescription())
                .weight(dish.getWeight())
                .restaurantId(dish.getRestaurant().getId())
                .restaurantName(dish.getRestaurant().getName())
                .build();
    }

    @Override
    public List<DishesDTO> getDishesByIds(List<Long> dishIds) {
        return dishRepository.findAllById(dishIds)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Long, Dishes> getDishesMapByIds(List<Long> ids) {
        return dishRepository.findAllById(ids)
                .stream()
                .collect(Collectors.toMap(Dishes::getId, dish -> dish));
    }

}