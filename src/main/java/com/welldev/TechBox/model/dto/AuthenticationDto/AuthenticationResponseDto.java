package com.welldev.TechBox.model.dto.AuthenticationDto;

import lombok.*;



@AllArgsConstructor
@Getter
public class AuthenticationResponseDto {
    private  String accessToken;
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private int mobilenumber;
    private String usertype;

}
