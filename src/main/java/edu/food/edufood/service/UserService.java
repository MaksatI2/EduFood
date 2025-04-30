package edu.food.edufood.service;

import edu.food.edufood.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    void registerUser(UserDTO user);
    Optional<UserDTO> findByEmail(String email);
}
