package edu.food.edufood.service;

import edu.food.edufood.dto.CartItemDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

public interface CartService {

    void removeFromCart(Long dishId, HttpServletRequest request, HttpServletResponse response);

    void updateCartItemQuantity(Long dishId, int quantity, HttpServletRequest request, HttpServletResponse response);

    void clearCart(HttpServletRequest request, HttpServletResponse response);

    void addToCart(Long dishId, int quantity, HttpServletRequest request, HttpServletResponse response);

    List<CartItemDTO> getCartItems(HttpServletRequest request);

    void transferCartItemsToUser(Long userId, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> prepareCartViewModel(HttpServletRequest request);

    void transferAnonymousCartToUser(Long userId, HttpServletRequest request, HttpServletResponse response);

    Integer getTotalItemsCount(HttpServletRequest request);

}