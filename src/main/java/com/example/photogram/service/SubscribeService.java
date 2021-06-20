package com.example.photogram.service;

import com.example.photogram.domain.Subscribe;
import com.example.photogram.domain.User;
import com.example.photogram.repository.SubscribeRepository;
import com.example.photogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void 구독하기(User user, Long toUserId) {

        Subscribe subscribe = new Subscribe();
        User byId = userRepository.findById(toUserId).get();

        subscribe.setFromUser(user);
        subscribe.setToUser(byId);

        subscribeRepository.save(subscribe);

    }

    @Transactional
    public void 구독취소하기(Long id, Long toUserId) {

        subscribeRepository.deleteSubscribeByFromUserAndAndToUser(id, toUserId);

    }
}
