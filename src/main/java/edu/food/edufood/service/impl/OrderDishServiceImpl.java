package edu.food.edufood.service.impl;

import edu.food.edufood.model.OrderDish;
import edu.food.edufood.repository.OrderDishRepository;
import edu.food.edufood.service.OrderDishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDishServiceImpl implements OrderDishService {
    private final OrderDishRepository orderDishRepository;

    @Override
    public void saveOrderDish(OrderDish orderDish) {
        orderDishRepository.save(orderDish);
    }
}