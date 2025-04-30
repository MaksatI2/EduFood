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
                .orders(restaurant.getOrders())
                .build();
    }
}