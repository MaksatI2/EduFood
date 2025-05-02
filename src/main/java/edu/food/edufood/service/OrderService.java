package edu.food.edufood.service;

import edu.food.edufood.model.Order;
import edu.food.edufood.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface OrderService {
    Order createOrderFromCart(User user, HttpServletRequest request, HttpServletResponse response);
}