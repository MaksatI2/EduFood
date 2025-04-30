package edu.food.edufood.repository;

import edu.food.edufood.dto.DishesDTO;
import edu.food.edufood.model.Dishes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishesRepository extends JpaRepository<Dishes, Long> {
    List<Dishes> findByRestaurantId(Long restaurantId);
}