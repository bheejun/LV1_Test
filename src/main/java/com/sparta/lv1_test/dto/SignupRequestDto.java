package com.sparta.lv1_test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter

public class SignupRequestDto {

        @Pattern(regexp = "^[a-z0-9]{4,10}$")
        private String username;
        @Pattern(regexp = "^[a-zA-Z0-9`~!@#$%^&*()-_=+]{8,15}$")
        private String password;
        @Email
        private String email;

        private boolean admin = false;

        private String adminToken="";
}
