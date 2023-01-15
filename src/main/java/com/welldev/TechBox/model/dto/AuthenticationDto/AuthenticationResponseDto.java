package com.welldev.TechBox.model.dto.AuthenticationDto;

import lombok.*;


@AllArgsConstructor
@Setter
@Getter
@Builder
public class AuthenticationResponseDto {
    private  String accessToken;
    private String refreshToken;

    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private int mobilenumber;
    private String usertype;

}
