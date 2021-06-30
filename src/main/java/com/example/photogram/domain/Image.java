package com.example.photogram.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String caption; // 오늘 나 너무 피곤해!!
    private String postImageUrl; // 사진을 전송받아서 그 사진을 서버에 특정 폴더에 저장 - DB에 그 저장된 경로를 insert

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.EAGER) // 이미지를 select하면 조인해서 User정보를 같이 들고옴
    private User user; // 1,  1

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
