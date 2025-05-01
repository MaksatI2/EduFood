package edu.food.edufood.service;

import edu.food.edufood.model.Order;
import edu.food.edufood.model.User;

public interface OrderService {

    Order createOrderFromCart(User user);
}