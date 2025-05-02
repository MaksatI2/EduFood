package edu.food.edufood.service.impl;

import edu.food.edufood.dto.UserDTO;
import edu.food.edufood.model.AccountType;
import edu.food.edufood.model.User;
import edu.food.edufood.repository.UserRepository;
import edu.food.edufood.service.CartService;
import edu.food.edufood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final CartService cartService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email уже используется");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setAccountType(AccountType.USER);
        user.setEnabled(true);

        User savedUser = userRepository.save(user);
        cartService.transferCartItemsToUser(savedUser);
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setEnabled(user.getEnabled());
                    dto.setAccountType(user.getAccountType());
                    return dto;
                });
    }

    @Override
    public Optional<User> getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}