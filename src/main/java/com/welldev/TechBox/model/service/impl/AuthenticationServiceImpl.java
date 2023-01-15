package com.welldev.TechBox.model.service.impl;


import com.welldev.TechBox.model.dao.UserDao;
import com.welldev.TechBox.model.dto.AuthenticationDto.AuthenticationRequestDto;
import com.welldev.TechBox.model.dto.AuthenticationDto.AuthenticationResponseDto;
import com.welldev.TechBox.model.dto.UserDto.UserRegisterRequestDto;
import com.welldev.TechBox.model.dto.UserDto.UserRegisterResponseDto;
import com.welldev.TechBox.model.entity.User;
import com.welldev.TechBox.model.rolesAndPermissions.AppUserRole;
import com.welldev.TechBox.model.service.AuthenticationService;
import com.welldev.TechBox.model.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public UserRegisterResponseDto register(UserRegisterRequestDto request){
        User user = null;


        switch (request.getUsertype()) {
            case "user":
                user = new User();
                user.setFirstname(request.getFirstname());
                user.setLastname(request.getLastname());
                user.setEmail(request.getEmail());
                user.setMobilenumber(request.getMobilenumber());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setUsertype(request.getUsertype());
                user.setAppUserRole(AppUserRole.USER);

                break;
            case "admin":
                user = new User();
                user.setFirstname(request.getFirstname());
                user.setLastname(request.getLastname());
                user.setEmail(request.getEmail());
                user.setMobilenumber(request.getMobilenumber());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setUsertype(request.getUsertype());
                user.setAppUserRole(AppUserRole.ADMIN);

                break;
            case "superadmin":
                user = new User();
                user.setFirstname(request.getFirstname());
                user.setLastname(request.getLastname());
                user.setEmail(request.getEmail());
                user.setMobilenumber(request.getMobilenumber());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setUsertype(request.getUsertype());
                user.setAppUserRole(AppUserRole.SUPER_ADMIN);
                break;
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

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        var user = userDao.findByEmail(request.getEmail());
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var jwtAccessToken = jwtService.generateAccessToken(user);
        return new AuthenticationResponseDto(
                jwtAccessToken,
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getMobilenumber(),
                user.getUsertype()
        );

    }
}
