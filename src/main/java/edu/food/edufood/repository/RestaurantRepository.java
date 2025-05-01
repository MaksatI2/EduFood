package edu.food.edufood.repository;

import edu.food.edufood.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select r from Restaurant r where lower(r.name) like lower(concat('%', :keyword, '%'))")
    Page<Restaurant> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}