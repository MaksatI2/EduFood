package edu.food.edufood.controller;

import edu.food.edufood.dto.CartItemDTO;
import edu.food.edufood.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public String addToCart(
            @RequestParam("dishId") Long dishId,
            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes,
            Model model) {

        List<CartItemDTO> cartItems = cartService.getCartItems(request);
        int currentQty = cartItems.stream()
                .filter(item -> item.getDishId().equals(dishId))
                .mapToInt(CartItemDTO::getQuantity)
                .sum();

        if (currentQty + quantity > 100) {
            redirectAttributes.addFlashAttribute("errorMessage", "Нельзя добавить более 100 штук одного блюда.");
        } else {
            cartService.addToCart(dishId, quantity, request, response);
            redirectAttributes.addFlashAttribute("successMessage", "Блюдо добавлено в корзину!");
        }

        model.addAttribute("cartItemCount", cartService.getTotalItemsCount(request));
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/dishes/list");
    }

    @PostMapping("/remove")
    public String removeFromCart(
            @RequestParam("dishId") Long dishId,
            HttpServletRequest request,
            HttpServletResponse response) {

        cartService.removeFromCart(dishId, request, response);
        return "redirect:/orders/cart";
    }

    @PostMapping("/update")
    public String updateCart(
            @RequestParam("dishId") Long dishId,
            @RequestParam("quantity") int quantity,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {

        if (quantity < 1 || quantity > 100) {
            redirectAttributes.addFlashAttribute("errorMessage", "Количество должно быть от 1 до 100.");
        } else {
            cartService.updateCartItemQuantity(dishId, quantity, request, response);
            redirectAttributes.addFlashAttribute("successMessage", "Количество обновлено.");
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/orders/cart");
    }

    @GetMapping
    public String viewCart(
            HttpServletRequest request,
            Model model) {

        Map<String, Object> cartViewModel = cartService.prepareCartViewModel(request);
        model.addAllAttributes(cartViewModel);

        int totalItems = cartService.getTotalItemsCount(request);
        model.addAttribute("cartItemCount", totalItems);

        return "cart/view";
    }

    @ModelAttribute("cartItemCount")
    public int getCartItemCount(HttpServletRequest request) {
        return cartService.getTotalItemsCount(request);
    }

    @PostMapping("/clear")
    public String clearCart(HttpServletResponse response, HttpServletRequest request) {
        cartService.clearCart(request, response);
        return "redirect:/orders/cart";
    }
}
