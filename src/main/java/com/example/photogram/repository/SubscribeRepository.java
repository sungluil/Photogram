package com.example.photogram.repository;

import com.example.photogram.domain.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Subscribe deleteSubscribeByFromUserAndAndToUser(Long fromUserId, Long toUserId);

}
