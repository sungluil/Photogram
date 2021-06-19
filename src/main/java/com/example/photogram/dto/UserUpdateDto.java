package com.example.photogram.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateDto {
    @NotBlank
    private String name; // 필수
    @NotBlank
    private String password; // 필수
    private String website;
    private String bio;
    private String phone;
    private String gender;
}
