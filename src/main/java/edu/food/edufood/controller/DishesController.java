package edu.food.edufood.controller;

import edu.food.edufood.model.Dishes;
import edu.food.edufood.service.DishesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishesController {
    private final DishesService dishService;

    @GetMapping
    public String getAllDishes(Model model) {
        List<Dishes> dishes = dishService.getAllDishes();
        model.addAttribute("dishes", dishes);
        return "dishes/list";
    }

    @GetMapping("/restaurants/{restaurantId}")
    public String getDishesByRestaurant(
            @PathVariable Long restaurantId,
            Model model
    ) {
        List<Dishes> dishes = dishService.getDishesByRestaurantId(restaurantId);
        model.addAttribute("dishes", dishes);
        return "dishes/list";
    }
}