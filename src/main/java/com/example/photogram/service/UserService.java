package com.example.photogram.service;

import com.example.photogram.domain.User;
import com.example.photogram.domain.UserDTO;
import com.example.photogram.dto.SignupDto;
import com.example.photogram.dto.UserProfileDto;
import com.example.photogram.dto.UserUpdateDto;
import com.example.photogram.handler.CustomException;
import com.example.photogram.handler.CustomValidationApiException;
import com.example.photogram.handler.CustomValidationException;
import com.example.photogram.mapper.UserDTOMapper;
import com.example.photogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;


    @Transactional
    public User signUp(SignupDto dto) {
        log.info("signUp 실행됨>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "존재하는 아이디입니다.");


        User byUsername = userRepository.findByUsername(dto.getUsername());
        if(byUsername != null) {
            throw new CustomValidationException("유효성 검사 실패함", errorMap);
        }

        User user = User
                .builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .name(dto.getName())
                .role("ROLE_USER")
                .build();

        log.info("user = {}", user);

        User save = userRepository.save(user);


        return save;
    }

    @Transactional
    public User update(Long id, UserUpdateDto userDTO) {
        System.out.println("update 실행됨>>>>>>>>>>>>>>>>>>>");
        User user = userRepository.findById(id).orElseThrow(()->new CustomValidationApiException("존재하는 아이디 입니다."));
        //UserDTO user = userRepository.findById(id).map(userDTOMapper::toDto).orElseThrow(()->new CustomValidationApiException("존재하는 아이디 입니다."));

        user.setName(userDTO.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setWebsite(userDTO.getWebsite());
        user.setBio(userDTO.getBio());
        user.setPhone(userDTO.getPhone());
        user.setGender(userDTO.getGender());

        System.out.println("업데이트 서비스 >>"+user);

        return user;
    }

    @Transactional
    public UserProfileDto 회원프로필(int userId, Long principalId) {
        UserProfileDto dto = new UserProfileDto();



        User userEntity = userRepository.findById((long) userId).orElseThrow(() -> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });

        dto.setUser(userEntity);
        dto.setPageOwnerState((long) userId == principalId);
        dto.setImageCount(userEntity.getImages().size());

        return dto;
    }
}
