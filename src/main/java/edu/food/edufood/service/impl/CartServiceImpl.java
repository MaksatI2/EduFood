package edu.food.edufood.service.impl;

import edu.food.edufood.dto.CartItemDTO;
import edu.food.edufood.dto.CartItemViewModelDTO;
import edu.food.edufood.dto.DishesDTO;
import edu.food.edufood.model.User;
import edu.food.edufood.service.CartService;
import edu.food.edufood.service.DishesService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final HttpSession httpSession;
    private final DishesService dishesService;

    private static final String CART_SESSION_KEY = "userCart";

    @Override
    public void addToCart(Long dishId, int quantity) {
        List<CartItemDTO> cartItems = getCartItemsFromSession();

        boolean dishExists = false;
        for (CartItemDTO item : cartItems) {
            if (Objects.equals(item.getDishId(), dishId)) {
                item.setQuantity(item.getQuantity() + quantity);
                dishExists = true;
                break;
            }
        }

        if (!dishExists) {
            CartItemDTO newItem = new CartItemDTO();
            newItem.setDishId(dishId);
            newItem.setQuantity(quantity);
            newItem.setSessionId(httpSession.getId());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
                if (auth.getPrincipal() instanceof CustomUserDetails userDetails) {
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

    @Override
    public Map<String, Object> prepareCartViewModel() {
        Map<String, Object> model = new HashMap<>();

        List<CartItemDTO> cartItems = getCartItems();
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
                viewModel.setRestaurantName(dish.getRestaurantName());
                viewModel.setTotalPrice(dish.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

                viewModels.add(viewModel);
                totalPrice = totalPrice.add(viewModel.getTotalPrice());
            }
        }

        model.put("cartItems", viewModels);
        model.put("totalPrice", totalPrice);

        return model;
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