package edu.food.edufood.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.food.edufood.dto.CartItemDTO;
import edu.food.edufood.dto.CartItemViewModelDTO;
import edu.food.edufood.dto.DishesDTO;
import edu.food.edufood.service.CartService;
import edu.food.edufood.service.DishesService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final DishesService dishesService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String CART_COOKIE_NAME = "userCart";
    private static final String ANONYMOUS_CART_COOKIE_NAME = "anonymousCart";
    private static final int ITEMS_PER_PAGE = 10;

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            if (auth.getPrincipal() instanceof CustomUserDetails userDetails) {
                return userDetails.getUserId();
            }
        }
        return null;
    }

    private String getCartCookieName(HttpServletRequest request) {
        Long userId = getCurrentUserId();
        if (userId != null) {
            return CART_COOKIE_NAME + "_" + userId;
        }
        return ANONYMOUS_CART_COOKIE_NAME + "_" + request.getSession().getId();
    }

    private List<CartItemDTO> getCartFromCookies(HttpServletRequest request) {
        String cartCookieName = getCartCookieName(request);
        try {
            for (Cookie cookie : Optional.ofNullable(request.getCookies()).orElse(new Cookie[0])) {
                if (cartCookieName.equals(cookie.getName())) {
                    String json = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                    return objectMapper.readValue(json, new TypeReference<>() {});
                }
            }
        } catch (Exception ignored) {}
        return new ArrayList<>();
    }

    private void saveCartToCookies(List<CartItemDTO> cartItems, HttpServletResponse response, HttpServletRequest request) {
        try {
            String cartCookieName = getCartCookieName(request);
            String json = objectMapper.writeValueAsString(cartItems);
            String encoded = URLEncoder.encode(json, StandardCharsets.UTF_8);
            Cookie cookie = new Cookie(cartCookieName, encoded);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFromCart(Long dishId, HttpServletRequest request, HttpServletResponse response) {
        List<CartItemDTO> cartItems = getCartFromCookies(request);
        cartItems.removeIf(item -> Objects.equals(item.getDishId(), dishId));
        saveCartToCookies(cartItems, response, request);
    }

    @Override
    public void updateCartItemQuantity(Long dishId, int quantity, HttpServletRequest request, HttpServletResponse response) {
        List<CartItemDTO> cartItems = getCartFromCookies(request);
        for (CartItemDTO item : cartItems) {
            if (Objects.equals(item.getDishId(), dishId)) {
                item.setQuantity(quantity);
                break;
            }
        }
        saveCartToCookies(cartItems, response, request);
    }

    @Override
    public void clearCart(HttpServletRequest request, HttpServletResponse response) {
        String cartCookieName = getCartCookieName(request);
        Cookie cookie = new Cookie(cartCookieName, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public void addToCart(Long dishId, int quantity, HttpServletRequest request, HttpServletResponse response) {
        List<CartItemDTO> cartItems = getCartFromCookies(request);

        for (CartItemDTO item : cartItems) {
            if (Objects.equals(item.getDishId(), dishId)) {
                item.setQuantity(item.getQuantity() + quantity);
                saveCartToCookies(cartItems, response, request);
                return;
            }
        }

        CartItemDTO newItem = new CartItemDTO();
        newItem.setDishId(dishId);
        newItem.setQuantity(quantity);

        Long userId = getCurrentUserId();
        if (userId != null) {
            newItem.setUserId(userId);
        }

        cartItems.add(newItem);
        saveCartToCookies(cartItems, response, request);
    }

    @Override
    public List<CartItemDTO> getCartItems(HttpServletRequest request) {
        return getCartFromCookies(request);
    }

    @Override
    public void transferCartItemsToUser(Long userId, HttpServletRequest request, HttpServletResponse response) {
        String anonymousCookieName = ANONYMOUS_CART_COOKIE_NAME + "_" + request.getSession().getId();
        List<CartItemDTO> anonymousCart = new ArrayList<>();

        try {
            for (Cookie cookie : Optional.ofNullable(request.getCookies()).orElse(new Cookie[0])) {
                if (anonymousCookieName.equals(cookie.getName())) {
                    String json = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                    anonymousCart = objectMapper.readValue(json, new TypeReference<>() {});
                    break;
                }
            }
        } catch (Exception ignored) {}

        if (anonymousCart.isEmpty()) return;
        String userCookieName = CART_COOKIE_NAME + "_" + userId;
        List<CartItemDTO> userCart = new ArrayList<>();

        try {
            for (Cookie cookie : Optional.ofNullable(request.getCookies()).orElse(new Cookie[0])) {
                if (userCookieName.equals(cookie.getName())) {
                    String json = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                    userCart = objectMapper.readValue(json, new TypeReference<>() {});
                    break;
                }
            }
        } catch (Exception ignored) {}
        Map<Long, CartItemDTO> userCartMap = userCart.stream()
                .collect(Collectors.toMap(CartItemDTO::getDishId, item -> item));

        for (CartItemDTO anonymousItem : anonymousCart) {
            anonymousItem.setUserId(userId);

            if (userCartMap.containsKey(anonymousItem.getDishId())) {
                CartItemDTO userItem = userCartMap.get(anonymousItem.getDishId());
                userItem.setQuantity(userItem.getQuantity() + anonymousItem.getQuantity());
            } else {
                userCart.add(anonymousItem);
            }
        }

        try {
            String json = objectMapper.writeValueAsString(userCart);
            String encoded = URLEncoder.encode(json, StandardCharsets.UTF_8);
            Cookie cookie = new Cookie(userCookieName, encoded);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Cookie clearCookie = new Cookie(anonymousCookieName, "");
        clearCookie.setPath("/");
        clearCookie.setMaxAge(0);
        response.addCookie(clearCookie);
    }

    @Override
    public Map<String, Object> prepareCartViewModel(HttpServletRequest request, int page) {
        Map<String, Object> model = new HashMap<>();
        List<CartItemDTO> cartItems = getCartItems(request);

        List<DishesDTO> dishes = dishesService.getDishesByIds(
                cartItems.stream().map(CartItemDTO::getDishId).toList());

        Map<Long, DishesDTO> dishMap = dishes.stream()
                .collect(Collectors.toMap(DishesDTO::getId, d -> d));

        BigDecimal total = BigDecimal.ZERO;
        List<CartItemViewModelDTO> allViewItems = new ArrayList<>();

        for (CartItemDTO item : cartItems) {
            DishesDTO dish = dishMap.get(item.getDishId());
            if (dish != null) {
                CartItemViewModelDTO view = new CartItemViewModelDTO();
                view.setDishId(item.getDishId());
                view.setDishName(dish.getName());
                view.setQuantity(item.getQuantity());
                view.setDishPrice(dish.getPrice());
                view.setRestaurantName(dish.getRestaurantName());
                view.setTotalPrice(dish.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

                total = total.add(view.getTotalPrice());
                allViewItems.add(view);
            }
        }

        int totalItems = allViewItems.size();
        int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);

        page = Math.max(1, Math.min(page, totalPages));

        int startIndex = (page - 1) * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, totalItems);

        List<CartItemViewModelDTO> paginatedItems = allViewItems.subList(startIndex, endIndex);

        model.put("cartItems", paginatedItems);
        model.put("totalPrice", total);
        model.put("currentPage", page);
        model.put("totalPages", totalPages);
        model.put("hasPrevious", page > 1);
        model.put("hasNext", page < totalPages);

        return model;
    }

    @Override
    public void transferAnonymousCartToUser(Long userId, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return;
        List<CartItemDTO> anonymousCartItems = new ArrayList<>();
        for (Cookie cookie : cookies) {
            if (cookie.getName().startsWith(ANONYMOUS_CART_COOKIE_NAME)) {
                try {
                    String json = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                    List<CartItemDTO> items = objectMapper.readValue(json, new TypeReference<>() {});
                    anonymousCartItems.addAll(items);
                    Cookie clearCookie = new Cookie(cookie.getName(), "");
                    clearCookie.setPath("/");
                    clearCookie.setMaxAge(0);
                    response.addCookie(clearCookie);
                } catch (Exception ignored) {}
            }
        }

        if (anonymousCartItems.isEmpty()) return;
        List<CartItemDTO> userCartItems = getCartFromCookies(request);
        Map<Long, CartItemDTO> userCartMap = userCartItems.stream()
                .collect(Collectors.toMap(CartItemDTO::getDishId, item -> item));

        for (CartItemDTO anonymousItem : anonymousCartItems) {
            anonymousItem.setUserId(userId);

            if (userCartMap.containsKey(anonymousItem.getDishId())) {
                CartItemDTO userItem = userCartMap.get(anonymousItem.getDishId());
                userItem.setQuantity(userItem.getQuantity() + anonymousItem.getQuantity());
            } else {
                userCartItems.add(anonymousItem);
            }
        }
        saveCartToCookies(userCartItems, response, request);
    }

    @Override
    public Integer getTotalItemsCount(HttpServletRequest request) {
        return getCartFromCookies(request).stream()
                .mapToInt(CartItemDTO::getQuantity)
                .sum();
    }
}