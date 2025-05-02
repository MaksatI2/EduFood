package edu.food.edufood.service;

import edu.food.edufood.dto.RestaurantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {
    Page<RestaurantDTO> findAll(Pageable pageable);


    Page<RestaurantDTO> findByName(String name, Pageable pageable);


}