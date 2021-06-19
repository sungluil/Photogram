package com.example.photogram.service;

import com.example.photogram.domain.User;
import com.example.photogram.domain.UserDTO;
import com.example.photogram.dto.SignupDto;
import com.example.photogram.mapper.UserDTOMapper;
import com.example.photogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;

    @Transactional
    public UserDTO signUp(SignupDto dto) {


        Optional<User> byUsername = userRepository.findByUsername(dto.getUsername());
        if(byUsername.isPresent()) {
            throw new IllegalArgumentException("존재하는 아이디입니다.");
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(dto, userDTO);
        userDTO.setPassword(passwordEncoder.encode(dto.getPassword()));
        userDTO.setRole("ROLE_USER"); // 관리자 ROLE_ADMIN
        log.info("userDTO = {}", userDTO);

        userRepository.save(userDTOMapper.toEntity(userDTO));


        return userDTO;
    }
}
