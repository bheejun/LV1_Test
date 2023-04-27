package com.sparta.lv1_test.service;

import com.sparta.lv1_test.entity.User;
import com.sparta.lv1_test.entity.UserRoleEnum;
import com.sparta.lv1_test.jwt.JwtUtil;
import com.sparta.lv1_test.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;



public class GetInformation {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    public GetInformation(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public String getUsernameFromToken(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token == null || !jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        String username = jwtUtil.getUserInfoFromToken(token).getSubject();
        return username;
    }

    public boolean isAdmin(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && user.getRole() == UserRoleEnum.ADMIN) {
            return true;
        }
        return false;
    }
}
