package edu.food.edufood.service.impl;

import edu.food.edufood.model.AccountType;
import edu.food.edufood.model.User;
import edu.food.edufood.repository.UserRepository;
import edu.food.edufood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email уже используется");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountType(AccountType.USER);
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}