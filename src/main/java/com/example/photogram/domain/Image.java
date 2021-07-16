package com.example.photogram.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
@EqualsAndHashCode(of = "id")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;

    private String postImageUrl;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;// 이미지를 select하면 조인해서 User정보를 같이 들고옴

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
