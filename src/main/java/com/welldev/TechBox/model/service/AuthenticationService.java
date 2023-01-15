package com.welldev.TechBox.model.service;


import com.welldev.TechBox.model.dao.UserDao;
import com.welldev.TechBox.model.dto.AuthenticationDto.AuthenticationRequestDto;
import com.welldev.TechBox.model.dto.AuthenticationDto.AuthenticationResponseDto;
import com.welldev.TechBox.model.dto.UserDto.UserRegisterRequestDto;
import com.welldev.TechBox.model.dto.UserDto.UserRegisterResponseDto;
import com.welldev.TechBox.model.entity.User;
import com.welldev.TechBox.model.rolesAndPermissions.AppUserRole;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public UserRegisterResponseDto register(UserRegisterRequestDto request) throws IOException {
        User user = null;

            if (request.getUsertype().equals("user")) {
                user = new User();
                user.setFirstname(request.getFirstname());
                user.setLastname(request.getLastname());
                user.setEmail(request.getEmail());
                user.setMobilenumber(request.getMobilenumber());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setUsertype(request.getUsertype());
                user.setAppUserRole(AppUserRole.USER);

            } else if (request.getUsertype().equals("admin")) {
                user = new User();
                user.setFirstname(request.getFirstname());
                user.setLastname(request.getLastname());
                user.setEmail(request.getEmail());
                user.setMobilenumber(request.getMobilenumber());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setUsertype(request.getUsertype());
                user.setAppUserRole(AppUserRole.ADMIN);

            } else if (request.getUsertype().equals("superadmin")) {
                user = new User();
                user.setFirstname(request.getFirstname());
                user.setLastname(request.getLastname());
                user.setEmail(request.getEmail());
                user.setMobilenumber(request.getMobilenumber());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setUsertype(request.getUsertype());
                user.setAppUserRole(AppUserRole.SUPER_ADMIN);
            }

        userDao.save(user);
        return new UserRegisterResponseDto(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getMobilenumber(),
                user.getUsertype());
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
