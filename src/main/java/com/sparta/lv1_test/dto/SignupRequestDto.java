package com.sparta.lv1_test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;
    @Pattern(regexp = "^[A-Za-z0-9]{4,10}$")
    private String password;
    @Email
    private String email;
    private boolean admin = false; // 관리자 권한 확인
    private String amdinToken = ""; //관리자 토큰
}
