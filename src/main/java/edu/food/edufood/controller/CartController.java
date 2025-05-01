package edu.food.edufood.controller;

import edu.food.edufood.dto.CartItemDTO;
import edu.food.edufood.dto.CartItemViewModelDTO;
import edu.food.edufood.dto.DishesDTO;
import edu.food.edufood.service.CartService;
import edu.food.edufood.service.DishesService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final DishesService dishesService;

    @PostMapping("/add")
    public String addToCart(
            @RequestParam("dishId") Long dishId,
            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        cartService.addToCart(dishId, quantity);
        redirectAttributes.addFlashAttribute("success", "Блюдо добавлено в корзину!");

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/dishes/list");
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("dishId") Long dishId) {
        cartService.removeFromCart(dishId);
        return "redirect:/orders/cart";
    }

    @PostMapping("/update")
    public String updateCartItemQuantity(@RequestParam("dishId") Long dishId,
                                         @RequestParam("quantity") int quantity) {
        cartService.updateCartItemQuantity(dishId, quantity);
        return "redirect:/orders/cart";
    }

    @GetMapping
    public String viewCart(Model model) {
        Map<String, Object> cartViewModel = cartService.prepareCartViewModel();
        model.addAllAttributes(cartViewModel);
        return "cart/view";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/orders/cart";
    }
}
