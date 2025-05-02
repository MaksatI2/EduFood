package edu.food.edufood.config;

import edu.food.edufood.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class CartInterceptor implements HandlerInterceptor {

    private final CartService cartService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (modelAndView != null) {
            int itemCount = cartService.getTotalItemsCount(request);
            modelAndView.addObject("cartItemCount", itemCount);
        }
    }
}
