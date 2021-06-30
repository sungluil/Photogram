package com.example.photogram.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity // 디비에 테이블을 생성
@ToString(of = {"id", "username", "name", "email", "images"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private Long id;

    @Column(length = 20,  unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String website; // 웹 사이트
    private String bio; // 자기 소개
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl; // 사진
    private String role; // 권한

    // 나는 연관관계의 주인이 아니다. 그러므로 테이블에 칼럼을 만들지마.
    // User를 Select할 때 해당 User id로 등록된 image들을 다 가져와
    // Lazy = User를 Select할 때 해당 User id로 등록된 image들을 가져오지마 - 대신 getImages() 함수의 image들이 호출될 때 가져와!!
    // Eager = User를 Select할 때 해당 User id로 등록된 image들을 전부 Join해서 가져와!!
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Image> images;


    private LocalDateTime createDate;

    @PrePersist // 디비에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
