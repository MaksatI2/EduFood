package edu.food.edufood.service;

import edu.food.edufood.dto.CartItemDTO;
import edu.food.edufood.model.User;

import java.util.List;
import java.util.Map;

public interface CartService {

    void addToCart(Long dishId, int quantity);

    void removeFromCart(Long dishId);

    void updateCartItemQuantity(Long dishId, int quantity);

    List<CartItemDTO> getCartItems();

    void clearCart();

    void transferCartItemsToUser(User user);

    Map<String, Object> prepareCartViewModel();
}