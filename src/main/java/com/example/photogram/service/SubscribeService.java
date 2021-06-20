package com.example.photogram.service;

import com.example.photogram.domain.Subscribe;
import com.example.photogram.domain.User;
import com.example.photogram.domain.UserDTO;
import com.example.photogram.dto.SubscribeDTO;
import com.example.photogram.mapper.SubscribeDTOMapper;
import com.example.photogram.mapper.UserDTOMapper;
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
    private final SubscribeDTOMapper subscribeDTOMapper;
    private final UserDTOMapper userDTOMapper;

    @Transactional
    public void 구독하기(User user, Long toUserId) {

        SubscribeDTO subscribeDTO = new SubscribeDTO();
        UserDTO userDTO1 = userRepository.findById(toUserId).map(userDTOMapper::toDto).get();

        subscribeDTO.setFromUser(user);
        subscribeDTO.setToUser(userDTOMapper.toEntity(userDTO1));

        subscribeRepository.save(subscribeDTOMapper.toEntity(subscribeDTO));

    }

    @Transactional
    public void 구독취소하기(Long id, Long toUserId) {

        subscribeRepository.deleteSubscribeByFromUserAndAndToUser(id, toUserId);

    }
}
