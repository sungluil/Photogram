package com.example.photogram.controller;

import com.example.photogram.dto.SignupDto;
import com.example.photogram.handler.CustomValidationException;
import com.example.photogram.repository.UserRepository;
import com.example.photogram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@Slf4j
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
    public String signup(@Valid SignupDto dto, Errors errors) throws Exception { // key=value (x-www-form-urlencoded)
        log.info("dto = {}", dto);
        Map<String, String> errorMap = new HashMap<>();
        if(errors.hasErrors()) {
            for(FieldError error : errors.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                System.out.println("====================");
                System.out.println(error.getDefaultMessage());
                System.out.println("====================");
            }
            throw new CustomValidationException("유효성 검사 실패함", errorMap);
        }

        userService.signUp(dto);
        return "auth/signin";
    }
}
