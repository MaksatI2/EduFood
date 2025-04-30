package edu.food.edufood.service;

import edu.food.edufood.model.User;

import java.util.Optional;

public interface UserService {
    void registerUser(User user);
    Optional<User> findByEmail(String email);
}
