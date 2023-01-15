package com.welldev.TechBox.controllers;


import com.welldev.TechBox.model.dto.AuthenticationDto.AuthenticationRequestDto;
import com.welldev.TechBox.model.dto.AuthenticationDto.AuthenticationResponseDto;
import com.welldev.TechBox.model.dto.UserRegisterDto.UserRegisterRequestDto;
import com.welldev.TechBox.model.service.AuthenticationService;
import com.welldev.TechBox.string.AUTH_URL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;


    @PostMapping(AUTH_URL.USER_REGISTRATION)
    public ResponseEntity<AuthenticationResponseDto> registerUser(
            @RequestBody UserRegisterRequestDto request,
            HttpServletResponse response
    ) throws IOException {
        return ResponseEntity.ok(authService.register(request, response));
    }

    @PostMapping(AUTH_URL.ADMIN_REGISTRATION)
    public ResponseEntity<AuthenticationResponseDto> registerAdmin(
            @RequestBody UserRegisterRequestDto request,
            HttpServletResponse response
    ) throws IOException {
        return ResponseEntity.ok(authService.register(request, response));
    }

    @PostMapping(AUTH_URL.AUTHENTICATE)
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody AuthenticationRequestDto request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(authService.authenticate(request, response));
    }
}
