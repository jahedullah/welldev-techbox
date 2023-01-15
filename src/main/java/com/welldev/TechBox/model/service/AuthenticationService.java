package com.welldev.TechBox.model.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.welldev.TechBox.model.dao.UserDao;
import com.welldev.TechBox.model.dto.AuthenticationDto.AuthenticationRequestDto;
import com.welldev.TechBox.model.dto.AuthenticationDto.AuthenticationResponseDto;
import com.welldev.TechBox.model.dto.UserRegisterDto.UserRegisterRequestDto;
import com.welldev.TechBox.model.entity.User;
import com.welldev.TechBox.model.rolesAndPermissions.AppUserRole;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthenticationResponseDto register(UserRegisterRequestDto request, HttpServletResponse response) throws IOException {
        User user = null;
        List emailList = userDao.findAllEmail();
        if (!emailList.contains(request.getEmail())) {

            if (request.getUsertype().equals("user")) {
                user = User.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .mobilenumber(request.getMobilenumber())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .usertype(request.getUsertype())
                        .appUserRole(AppUserRole.USER)
                        .build();
            } else if (request.getUsertype().equals("admin")) {
                user = User.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .mobilenumber(request.getMobilenumber())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .usertype(request.getUsertype())
                        .appUserRole(AppUserRole.ADMIN)
                        .build();

            } else if (request.getUsertype().equals("superadmin")) {
                user = User.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .mobilenumber(request.getMobilenumber())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .usertype(request.getUsertype())
                        .appUserRole(AppUserRole.SUPER_ADMIN)
                        .build();

            }
        } else {
            response.setStatus(BAD_REQUEST.value());
            Map<String, String> error = new HashMap<>();
            error.put("Oops! Email has already been taken", "Use different email.");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }

        userDao.save(user);
        var jwtAccessToken = jwtService.generateAccessToken(user);
        var jwtRefreshToken = jwtService.generateRefreshToken(user);
        // Adding these two token into response header.
        response.setHeader("AccessToken", jwtAccessToken);
        response.setHeader("RefreshToken", jwtRefreshToken);

        return AuthenticationResponseDto.builder()
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();

    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request, HttpServletResponse response) {
        var user = userDao.findByEmail(request.getEmail());
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var jwtAccessToken = jwtService.generateAccessToken(user);
        var jwtRefreshToken = jwtService.generateRefreshToken(user);

        // Adding these two token into response header.
        response.setHeader("AccessToken", jwtAccessToken);
        response.setHeader("RefreshToken", jwtRefreshToken);
        return AuthenticationResponseDto.builder()
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();

    }
}
