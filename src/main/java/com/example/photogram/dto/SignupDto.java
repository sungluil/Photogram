package com.example.photogram.dto;

import com.example.photogram.domain.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupDto {
    // https://bamdule.tistory.com/35 (@Valid 어노테이션 종류)
    @Size(min = 2, max = 20)
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String name;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }
}
