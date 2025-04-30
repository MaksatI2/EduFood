package edu.food.edufood.controller;

import edu.food.edufood.model.Order;
import edu.food.edufood.model.User;
import edu.food.edufood.service.OrderService;
import edu.food.edufood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/create")
    public String createOrder(RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            redirectAttributes.addFlashAttribute("authError", "Для оформления заказа необходимо войти в систему");
            return "redirect:/auth/login?checkout=true";
        }

        String userEmail = auth.getName();
        User user = userService.getUserEntityByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден"));

        Order order = orderService.createOrderFromCart(user);

        return "redirect:/orders/confirmation/" + order.getId();
    }
}
