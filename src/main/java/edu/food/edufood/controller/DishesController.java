package edu.food.edufood.controller;

import edu.food.edufood.dto.DishesDTO;
import edu.food.edufood.service.DishesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.stream.IntStream;


@Controller
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishesController {
    private final DishesService dishService;

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_PAGE = 0;

    @GetMapping
    public String getAllDishes(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            Model model
    ) {
        int currentPage = page.orElse(DEFAULT_PAGE);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<DishesDTO> dishesPage = dishService.getAllDishes(
                PageRequest.of(currentPage, pageSize)
        );

        model.addAttribute("dishes", dishesPage);
        model.addAttribute("dishesPage", dishesPage);
        int totalPages = dishesPage.getTotalPages();
        if (totalPages > 0) {
            model.addAttribute("pageNumbers",
                    IntStream.rangeClosed(0, totalPages - 1).boxed().toList());
        }

        return "dishes/list";
    }

    @GetMapping("/restaurants/{restaurantId}")
    public String getDishesByRestaurant(
            @PathVariable Long restaurantId,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            Model model
    ) {
        int currentPage = page.orElse(DEFAULT_PAGE);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<DishesDTO> dishesPage = dishService.getDishesByRestaurantId(
                restaurantId,
                PageRequest.of(currentPage, pageSize)
        );

        model.addAttribute("dishes", dishesPage);

        int totalPages = dishesPage.getTotalPages();
        if (totalPages > 0) {
            model.addAttribute("pageNumbers",
                    IntStream.rangeClosed(0, totalPages - 1).boxed().toList());
        }

        return "dishes/list";
    }

}