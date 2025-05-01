package edu.food.edufood.service;

import edu.food.edufood.dto.OrderHistoryDTO;

import java.util.List;

public interface OrderHistoryService {
    List<OrderHistoryDTO> getOrderHistoryForUser(Long userId);
}
