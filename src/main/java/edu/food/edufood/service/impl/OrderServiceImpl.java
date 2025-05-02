package edu.food.edufood.service.impl;

import edu.food.edufood.dto.CartItemDTO;
import edu.food.edufood.model.Dishes;
import edu.food.edufood.model.Order;
import edu.food.edufood.model.OrderDish;
import edu.food.edufood.model.User;
import edu.food.edufood.repository.OrderDishRepository;
import edu.food.edufood.repository.OrderRepository;
import edu.food.edufood.service.CartService;
import edu.food.edufood.service.DishesService;
import edu.food.edufood.service.OrderDishService;
import edu.food.edufood.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final DishesService dishesService;
    private final OrderDishService orderDishService;

    @Override
    public Order createOrderFromCart(User user) {
        List<CartItemDTO> cartItems = cartService.getCartItems();

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Корзина пуста");
        }

        List<Long> dishIds = cartItems.stream()
                .map(CartItemDTO::getDishId)
                .toList();

        Map<Long, Dishes> dishesMap = dishesService.getDishesMapByIds(dishIds);

        for (CartItemDTO item : cartItems) {
            if (!dishesMap.containsKey(item.getDishId())) {
                throw new IllegalStateException("Блюдо с ID " + item.getDishId() + " не найдено");
            }
        }

        BigDecimal totalPrice = cartItems.stream()
                .map(item -> dishesMap.get(item.getDishId()).getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .user(user)
                .totalPrice(totalPrice)
                .createdAt(LocalDateTime.now())
                .sessionId(cartItems.get(0).getSessionId())
                .build();

        Order savedOrder = orderRepository.save(order);

        for (CartItemDTO item : cartItems) {
            OrderDish orderDish = OrderDish.builder()
                    .order(savedOrder)
                    .dish(dishesMap.get(item.getDishId()))
                    .quantity(item.getQuantity())
                    .build();

            orderDishService.saveOrderDish(orderDish);
        }

        cartService.clearCart();
        return savedOrder;
    }
}