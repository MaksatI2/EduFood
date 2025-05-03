package edu.food.edufood.service.impl;

import edu.food.edufood.dto.OrderDishDetailsDTO;
import edu.food.edufood.dto.OrderHistoryDTO;
import edu.food.edufood.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import edu.food.edufood.model.OrderDish;
import edu.food.edufood.repository.OrderRepository;
import edu.food.edufood.service.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderHistoryServiceImpl implements OrderHistoryService {
    private final OrderRepository orderRepository;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public Page<OrderHistoryDTO> getOrderHistoryForUser(Long userId, Pageable pageable) {
        List<Order> orders = orderRepository.findAllByUserId(userId);

        List<OrderHistoryDTO> orderDTOs = orders.stream().map(order -> {
            List<OrderDishDetailsDTO> dishes = order.getOrderDishes().stream()
                    .map(this::mapToOrderDishDetailsDTO)
                    .collect(Collectors.toList());

            return OrderHistoryDTO.builder()
                    .id(order.getId())
                    .createdAt(order.getCreatedAt())
                    .formattedCreatedAt(order.getCreatedAt().format(FORMATTER))
                    .totalPrice(order.getTotalPrice())
                    .dishes(dishes)
                    .build();
        }).collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), orderDTOs.size());
        List<OrderHistoryDTO> pageContent = orderDTOs.subList(start, end);

        return new PageImpl<>(pageContent, pageable, orderDTOs.size());
    }

    private OrderDishDetailsDTO mapToOrderDishDetailsDTO(OrderDish orderDish) {
        return OrderDishDetailsDTO.builder()
                .name(orderDish.getDish().getName())
                .photoUrl(orderDish.getDish().getPhotoUrl())
                .price(orderDish.getDish().getPrice())
                .quantity(orderDish.getQuantity())
                .restaurantName(orderDish.getDish().getRestaurant().getName())
                .restaurantPhotoUrl(orderDish.getDish().getRestaurant().getPhotoUrl())
                .build();
    }
}