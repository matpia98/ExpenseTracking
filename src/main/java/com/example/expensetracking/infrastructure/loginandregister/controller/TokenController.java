package com.example.expensetracking.infrastructure.loginandregister.controller;

import com.example.expensetracking.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import com.example.expensetracking.infrastructure.loginandregister.controller.dto.TokenRequestDto;
import com.example.expensetracking.infrastructure.security.jwt.JwtAuthenticator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TokenController {

    private final JwtAuthenticator jwtAuthenticatorFacade;

    @PostMapping("/token")
    public ResponseEntity<JwtResponseDto> authenticateAndGenerateToken(@Valid @RequestBody TokenRequestDto tokenRequest) {
        final JwtResponseDto jwtResponse = jwtAuthenticatorFacade.authenticateAndGenerateToken(tokenRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}
