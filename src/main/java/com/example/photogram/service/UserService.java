package com.example.photogram.service;

import com.example.photogram.domain.User;
import com.example.photogram.domain.UserDTO;
import com.example.photogram.dto.SignupDto;
import com.example.photogram.handler.CustomValidationException;
import com.example.photogram.mapper.UserDTOMapper;
import com.example.photogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
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
        log.info("signUp 실행됨>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "존재하는 아이디입니다.");


        Optional<User> byUsername = userRepository.findByUsername(dto.getUsername());
        if(byUsername.isPresent()) {
            throw new CustomValidationException("유효성 검사 실패함", errorMap);
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
