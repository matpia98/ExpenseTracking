package com.example.expensetracking.domain.loginandregister;

import com.example.expensetracking.domain.loginandregister.dto.RegisterUserDto;
import com.example.expensetracking.domain.loginandregister.dto.RegistrationResultDto;
import com.example.expensetracking.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LoginAndRegisterFacade {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public RegistrationResultDto registerUser(RegisterUserDto registerUserDto) {
        userValidator.validate(registerUserDto.username(), registerUserDto.password());
        User savedUser = userRepository.save(User.builder()
                .username(registerUserDto.username())
                .password(registerUserDto.password())
                .build());
        return new RegistrationResultDto(savedUser.getId().toString(), savedUser.getUsername(), true);
    }

    public UserDto findUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("User with username: " + username + " not found"));
        return UserDto.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}