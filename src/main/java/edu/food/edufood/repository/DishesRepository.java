package edu.food.edufood.repository;

import edu.food.edufood.model.Dishes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DishesRepository extends JpaRepository<Dishes, Long> {
    Page<Dishes> findByRestaurantId(Long restaurantId, Pageable pageable);
    Page<Dishes> findAll(Pageable pageable);
}