package edu.food.edufood.service;

import edu.food.edufood.dto.OrderHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderHistoryService {
    Page<OrderHistoryDTO> getOrderHistoryForUser(Long userId, Pageable pageable);
}
