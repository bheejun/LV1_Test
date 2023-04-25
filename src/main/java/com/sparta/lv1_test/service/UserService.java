package com.sparta.lv1_test.service;

import com.sparta.lv1_test.dto.LoginRequestDto;
import com.sparta.lv1_test.dto.SignupRequestDto;
import com.sparta.lv1_test.entity.User;
import com.sparta.lv1_test.entity.UserRoleEnum;
import com.sparta.lv1_test.jwt.JwtUtil;
import com.sparta.lv1_test.repository.UserRepsotiry;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepsotiry userRepsotiry;

    private final JwtUtil jwtUtil;


    @Transactional
    public String signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String email = signupRequestDto.getEmail();

        UserRoleEnum role = UserRoleEnum.USER;
        //회원 중복 확인
        Optional<User> userfind = userRepsotiry.findByUsername(username);
        if (userfind.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다");
        }

        User user = new User(username, password, email, role);
        userRepsotiry.save(user);

        return "success";

    }


    public String login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepsotiry.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다"));

        //비밀번호 확인
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return "success";
    }

}
