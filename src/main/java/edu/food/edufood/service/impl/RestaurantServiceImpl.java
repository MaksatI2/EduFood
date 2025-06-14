package edu.food.edufood.service.impl;

import edu.food.edufood.dto.RestaurantDTO;
import edu.food.edufood.model.Restaurant;
import edu.food.edufood.repository.RestaurantRepository;
import edu.food.edufood.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Page<RestaurantDTO> findAll(Pageable pageable) {
        return restaurantRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    private RestaurantDTO convertToDto(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .photoUrl(restaurant.getPhotoUrl())
                .dishes(restaurant.getDishes())
                .build();
    }

    @Override
    public Page<RestaurantDTO> findByName(String name, Pageable pageable) {
        return restaurantRepository.findByKeyword(name, pageable)
                .map(this::convertToDto);
    }

    public Restaurant getById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Ресторан не найден"));
    }
}