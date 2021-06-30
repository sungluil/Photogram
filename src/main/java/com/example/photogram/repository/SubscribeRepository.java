package com.example.photogram.repository;

import com.example.photogram.domain.Subscribe;
import com.example.photogram.domain.User;
import com.example.photogram.dto.SubscribeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Subscribe deleteSubscribeByFromUserAndAndToUser(Long fromUserId, Long toUserId);

    @Modifying // INSERT, DELETE, UPDATE 를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요!!
    @Query(value = "INSERT INTO subscribe(from_user_id, to_user_id, create_time) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
    void mSubscribe(Long fromUserId, Long toUserId);

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE from_user_id = :fromUserId AND to_user_id = :toUserId", nativeQuery = true)
    void mUnSubscribe(Long fromUserId, Long toUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE from_user_id = :principalId AND to_user_id = :pageUserId", nativeQuery = true)
    int mSubscribeState(Long principalId, int pageUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE from_user_id = :pageUserId", nativeQuery = true)
    int mSubscribeCount(int pageUserId);

}
