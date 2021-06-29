package com.example.photogram.domain;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String website; // 웹 사이트
    private String bio; // 자기 소개
    private String phone;
    private String gender;
    private String profileImageUrl; // 사진
    private String role; // 권한
}
