package edu.food.edufood.service;

import edu.food.edufood.dto.RestaurantDTO;
import edu.food.edufood.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {
    Page<RestaurantDTO> findAll(Pageable pageable);
    Restaurant getById(Long id);
}