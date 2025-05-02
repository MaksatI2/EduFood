package edu.food.edufood.controller;

import edu.food.edufood.dto.RestaurantDTO;
import edu.food.edufood.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final RestaurantService restaurantService;

    @GetMapping("/")
    public String mainPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String search,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RestaurantDTO> restaurantDTO;

        if (search != null) {
            restaurantDTO = restaurantService.findByName(search, pageable);
            model.addAttribute("restaurants", restaurantDTO.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", restaurantDTO.getTotalPages());
            model.addAttribute("totalItems", restaurantDTO.getTotalElements());
            model.addAttribute("search", search);
        }else {
            model.addAttribute("restaurants", Collections.emptyList());
        }

        return "main";
    }

}
