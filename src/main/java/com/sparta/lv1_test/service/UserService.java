package com.sparta.lv1_test.service;

import com.sparta.lv1_test.dto.LoginRequestDto;
import com.sparta.lv1_test.dto.SignupRequestDto;
import com.sparta.lv1_test.entity.User;
import com.sparta.lv1_test.entity.UserRoleEnum;
import com.sparta.lv1_test.jwt.JwtUtil;
import com.sparta.lv1_test.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public String signup(SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String email = signupRequestDto.getEmail();

//        if(username.length()<=10)


        Optional<User> validate = userRepository.findByUsername(username);


        if(validate.isPresent()){
            throw new IllegalArgumentException("중복됨");

        }

        UserRoleEnum role = UserRoleEnum.USER;
        if(signupRequestDto.isAdmin()){
            if(!signupRequestDto.getAdminTorken().equals(ADMIN_TOKEN)){
                throw new IllegalArgumentException("관리자 암호가 틀렸음");
            }

            role = UserRoleEnum.ADMIN;
        }


        User user = new User(username, password, email, role);
        userRepository.save(user);
        return "success";
    }

    @Transactional(readOnly = true)
    public String login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        String userId = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(userId).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));

        return "success";


    }



}
