package edu.food.edufood.repository;

import edu.food.edufood.model.Dishes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishesRepository extends JpaRepository<Dishes, Long> {
    List<Dishes> findByRestaurantId(Long restaurantId);
    Page<Dishes> findAll(Pageable pageable);
}