package com.example.photogram.repository;

import com.example.photogram.domain.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {

    // 이미지를 조회하는데 이미지의 유저아이디가 구독테이블의 구독자아이디와와 로그인사용자아이디가 같은것만 조회
    @Query(value = "" +
            "SELECT * " +
              "FROM image " +
             "WHERE userId " +
                "IN (SELECT toUserId " +
                      "FROM subscribe " +
                     "WHERE fromUserId = :principalId) " +
             "ORDER BY id DESC", nativeQuery = true)
    Page<Image> mStory(long principalId, Pageable pageable);
}
