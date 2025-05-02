package edu.food.edufood.service;

import edu.food.edufood.dto.UserDTO;
import edu.food.edufood.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public interface UserService {
    void registerUser(UserDTO dto, HttpServletRequest request, HttpServletResponse response);
    Optional<UserDTO> findByEmail(String email);
    Optional<User> getUserEntityByEmail(String email);
}
