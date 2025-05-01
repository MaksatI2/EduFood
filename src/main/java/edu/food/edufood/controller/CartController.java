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
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        cartService.addToCart(dishId);
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
        List<CartItemDTO> cartItems = cartService.getCartItems();

        List<Long> dishIds = cartItems.stream()
                .map(CartItemDTO::getDishId)
                .collect(Collectors.toList());

        List<DishesDTO> dishes = dishesService.getDishesByIds(dishIds);
        Map<Long, DishesDTO> dishesMap = dishes.stream()
                .collect(Collectors.toMap(DishesDTO::getId, dish -> dish));

        List<CartItemViewModelDTO> viewModels = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItemDTO item : cartItems) {
            DishesDTO dish = dishesMap.get(item.getDishId());
            if (dish != null) {
                CartItemViewModelDTO viewModel = new CartItemViewModelDTO();
                viewModel.setId(item.getId());
                viewModel.setDishId(item.getDishId());
                viewModel.setDishName(dish.getName());
                viewModel.setDishPrice(dish.getPrice());
                viewModel.setQuantity(item.getQuantity());
                viewModel.setTotalPrice(dish.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

                viewModels.add(viewModel);
                totalPrice = totalPrice.add(viewModel.getTotalPrice());
            }
        }

        model.addAttribute("cartItems", viewModels);
        model.addAttribute("totalPrice", totalPrice);

        return "cart/view";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/orders/cart";
    }
}
