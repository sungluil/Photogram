package com.example.photogram.service;

import com.example.photogram.domain.Image;
import com.example.photogram.domain.User;
import com.example.photogram.domain.UserDTO;
import com.example.photogram.dto.SignupDto;
import com.example.photogram.dto.UserProfileDto;
import com.example.photogram.dto.UserUpdateDto;
import com.example.photogram.handler.CustomException;
import com.example.photogram.handler.CustomValidationApiException;
import com.example.photogram.handler.CustomValidationException;
import com.example.photogram.mapper.UserDTOMapper;
import com.example.photogram.repository.SubscribeRepository;
import com.example.photogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubscribeRepository subscribeRepository;
    private final UserDTOMapper userDTOMapper;

    public List<UserDTO> findAll() {
        Long id = 1L;
        userRepository.findById(id).map(UserDTO::new);
        userRepository.findAll().stream().map(UserDTO::new);


        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserProfileDto 회원프로필(int pageUserId, Long principalId) {
        UserProfileDto dto = new UserProfileDto();

        // SELECT * FROM image WHERE userId = :userId;
        User userEntity = userRepository.findById((long) pageUserId).orElseThrow(()-> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });
        System.out.println("실행1111111"+userEntity);
        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserId == principalId);
        dto.setImageCount(userEntity.getImages().size());

        int subscribeState =  subscribeRepository.mSubscribeState(principalId, pageUserId);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

        dto.setSubscribeState(subscribeState == 1);
        dto.setSubscribeCount(subscribeCount);

        List<Image> collect = dto.getUser().getImages().stream().sorted(Comparator.comparing(Image::getId).reversed()).collect(Collectors.toList());
        dto.getUser().setImages(collect);

        return dto;
    }

    @Transactional
    public UserDTO signUp(SignupDto dto) {
        log.info("signUp 실행됨>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "존재하는 아이디입니다.");


        User byUsername = userRepository.findByUsername(dto.getUsername());
        if(byUsername != null) {
            throw new CustomValidationException("유효성 검사 실패함", errorMap);
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(dto, userDTO);
        userDTO.setPassword(passwordEncoder.encode(dto.getPassword()));
        userDTO.setRole("ROLE_USER"); // 관리자 ROLE_ADMIN
        log.info("userDTO = {}", userDTO);

        User save = userRepository.save(userDTOMapper.toEntity(userDTO));

        log.info("save = {}", save);

        return userDTO;
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDto userDTO) {
        System.out.println("업데이트 실행됨>>>>>>>>>>>>>>>>>>>");
        User user = userRepository.findById(id).orElseThrow(()->new CustomValidationApiException("존재하는 아이디 입니다."));
        //UserDTO user = userRepository.findById(id).map(userDTOMapper::toDto).orElseThrow(()->new CustomValidationApiException("존재하는 아이디 입니다."));

        user.setName(userDTO.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setWebsite(userDTO.getWebsite());
        user.setBio(userDTO.getBio());
        user.setPhone(userDTO.getPhone());
        user.setGender(userDTO.getGender());

        System.out.println("업데이트 서비스 >>"+user);

        return userDTOMapper.toDto(user);
    }
}
