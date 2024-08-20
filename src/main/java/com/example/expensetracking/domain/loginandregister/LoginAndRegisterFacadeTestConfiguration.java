package com.example.expensetracking.domain.loginandregister;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
class LoginAndRegisterFacadeTestConfiguration {

    @Bean
    LoginAndRegisterFacade createForTest(UserRepository userRepository) {
        UserValidator userValidator = new UserValidator(userRepository);
        return new LoginAndRegisterFacade(userRepository, userValidator);
    }

}
