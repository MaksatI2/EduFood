package edu.food.edufood.service.impl;

import edu.food.edufood.dto.CartItemDTO;
import edu.food.edufood.model.User;
import edu.food.edufood.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final HttpSession httpSession;

    private static final String CART_SESSION_KEY = "userCart";

    @Override
    public void addToCart(Long dishId) {
        List<CartItemDTO> cartItems = getCartItemsFromSession();

        boolean dishExists = false;
        for (CartItemDTO item : cartItems) {
            if (Objects.equals(item.getDishId(), dishId)) {
                item.setQuantity(item.getQuantity() + 1);
                dishExists = true;
                break;
            }
        }
        if (!dishExists) {
            CartItemDTO newItem = new CartItemDTO();
            newItem.setDishId(dishId);
            newItem.setQuantity(1);
            newItem.setSessionId(httpSession.getId());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
                if (auth.getPrincipal() instanceof CustomUserDetails) {
                    CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
                    newItem.setUserId(userDetails.getUserId());
                }
            }

            cartItems.add(newItem);
        }

        httpSession.setAttribute(CART_SESSION_KEY, cartItems);
    }

    @Override
    public void removeFromCart(Long dishId) {
        List<CartItemDTO> cartItems = getCartItemsFromSession();
        cartItems.removeIf(item -> Objects.equals(item.getDishId(), dishId));
        httpSession.setAttribute(CART_SESSION_KEY, cartItems);
    }

    @Override
    public void updateCartItemQuantity(Long dishId, int quantity) {
        List<CartItemDTO> cartItems = getCartItemsFromSession();
        for (CartItemDTO item : cartItems) {
            if (Objects.equals(item.getDishId(), dishId)) {
                item.setQuantity(quantity);
                break;
            }
        }
        httpSession.setAttribute(CART_SESSION_KEY, cartItems);
    }

    @Override
    public List<CartItemDTO> getCartItems() {
        return getCartItemsFromSession();
    }

    @Override
    public void clearCart() {
        httpSession.removeAttribute(CART_SESSION_KEY);
    }

    @Override
    public void transferCartItemsToUser(User user) {
        List<CartItemDTO> cartItems = getCartItemsFromSession();

        for (CartItemDTO item : cartItems) {
            item.setUserId(user.getId());
        }

        httpSession.setAttribute(CART_SESSION_KEY, cartItems);
    }

    private List<CartItemDTO> getCartItemsFromSession() {
        @SuppressWarnings("unchecked")
        List<CartItemDTO> cartItems = (List<CartItemDTO>) httpSession.getAttribute(CART_SESSION_KEY);

        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        return cartItems;
    }
}