package com.example.photogram.domain;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String website; // 웹 사이트
    private String bio; // 자기 소개
    private String email;
    private String phone;
    private String gender;
    private String profileImageUrl; // 사진
    private String role; // 권한

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.website = user.getWebsite();
        this.bio = user.getBio();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.gender = user.getGender();
        this.profileImageUrl = user.getProfileImageUrl();
        this.role = user.getRole();
    }
}
