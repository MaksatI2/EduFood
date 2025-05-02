package edu.food.edufood.controller;

import edu.food.edufood.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class BaseController {

    @Autowired
    private CartService cartService;

    @ModelAttribute("cartItemCount")
    public int getCartItemCount(HttpServletRequest request) {
        return cartService.getTotalItemsCount(request);
    }
}