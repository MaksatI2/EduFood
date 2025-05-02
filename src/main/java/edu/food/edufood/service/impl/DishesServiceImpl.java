package edu.food.edufood.service.impl;

import edu.food.edufood.dto.DishesDTO;
import edu.food.edufood.model.Dishes;
import edu.food.edufood.repository.DishesRepository;
import edu.food.edufood.service.DishesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishesServiceImpl implements DishesService {
    private final DishesRepository dishRepository;

    @Override
    public Page<DishesDTO> getAllDishes(Pageable pageable) {
        Pageable limitedPageable = pageable.getPageSize() > 10
                ? PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort())
                : pageable;
        return dishRepository.findAll(limitedPageable)
                .map(this::convertToDTO);
    }

    @Override
    public Page<DishesDTO> getDishesByRestaurantId(Long restaurantId, Pageable pageable) {
        return dishRepository.findByRestaurantId(restaurantId, pageable)
                .map(this::convertToDTO);
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

    @Override
    public Dishes getDishById(Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new RuntimeException("Блюдо не найдено"));
    }

}