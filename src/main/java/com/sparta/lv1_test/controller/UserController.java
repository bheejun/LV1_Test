package com.sparta.lv1_test.controller;

import com.sparta.lv1_test.dto.LoginRequestDto;
import com.sparta.lv1_test.dto.SignupRequestDto;
import com.sparta.lv1_test.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){

        return userService.login(loginRequestDto, response);
    }

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
//        System.out.println("username "+signupRequestDto.getUserId());

        return userService.signup(signupRequestDto);
    }



}
