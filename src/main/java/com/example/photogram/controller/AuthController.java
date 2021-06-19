package com.example.photogram.controller;

import com.example.photogram.domain.User;
import com.example.photogram.domain.UserDTO;
import com.example.photogram.dto.SignupDto;
import com.example.photogram.repository.UserRepository;
import com.example.photogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    // 회원가입버튼 -> /auth/signup -> /auth/signin
    // 회원가입버튼 X
    @PostMapping("/auth/signup")
    public String signup(SignupDto dto) { // key=value (x-www-form-urlencoded)
        System.out.println(dto);
        Optional<User> byUsername = userRepository.findByUsername(dto.getUsername());

        if(byUsername.isPresent()) {
            throw new IllegalArgumentException("존재하는 유저네임입니다.");
        }
        userService.signUp(dto);
        return "auth/signin";
    }
}
