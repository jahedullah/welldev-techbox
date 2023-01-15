package com.welldev.TechBox.model.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRegisterResponseDto {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private int mobilenumber;
    private String usertype;
}
