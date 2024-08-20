package com.example.expensetracking.domain.loginandregister;

import com.example.expensetracking.domain.loginandregister.exceptions.InvalidRegistrationDataException;
import com.example.expensetracking.domain.loginandregister.exceptions.UsernameNotUniqueException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class UserValidator {

    private final UserRepository userRepository;
    void validate(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameNotUniqueException("Username: " + username + " already exists");
        }
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidRegistrationDataException("Invalid username or password data");
        }
    }
}
