package edu.food.edufood.controller;

import edu.food.edufood.dto.OrderHistoryDTO;
import edu.food.edufood.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import edu.food.edufood.model.User;
import edu.food.edufood.service.OrderHistoryService;
import edu.food.edufood.service.OrderService;
import edu.food.edufood.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderHistoryService orderHistoryService;
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/create")
    public String createOrder(HttpServletRequest request,
                              HttpServletResponse response,
                              RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            redirectAttributes.addFlashAttribute("authError", "Для оформления заказа необходимо войти в систему");
            return "redirect:/auth/login?checkout=true";
        }

        String userEmail = auth.getName();
        User user = userService.getUserEntityByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден"));

        Order order = orderService.createOrderFromCart(user, request, response);

        redirectAttributes.addFlashAttribute("success",
                "Заказ №" + order.getId() + " успешно оформлен! Спасибо за покупку!");

        return "redirect:/profile";
    }

    @GetMapping("/history")
    public String getOrderHistory(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size,
                                  Model model,
                                  Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userService.getUserEntityByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден"));

        Pageable pageable = PageRequest.of(page, size);
        Page<OrderHistoryDTO> ordersPage = orderHistoryService.getOrderHistoryForUser(user.getId(), pageable);

        model.addAttribute("ordersPage", ordersPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ordersPage.getTotalPages());

        return "cart/history";
    }
}
