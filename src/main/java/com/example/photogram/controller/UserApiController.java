package com.example.photogram.controller;

import com.example.photogram.config.PrincipalDetails;
import com.example.photogram.domain.User;
import com.example.photogram.domain.UserDTO;
import com.example.photogram.dto.CMRespDto;
import com.example.photogram.dto.SubscribeDTO;
import com.example.photogram.dto.UserUpdateDto;
import com.example.photogram.handler.CustomValidationApiException;
import com.example.photogram.handler.CustomValidationException;
import com.example.photogram.mapper.UserDTOMapper;
import com.example.photogram.service.SubscribeService;
import com.example.photogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final UserDTOMapper userDTOMapper;
    private final SubscribeService subscribeService;

    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails){

        List<SubscribeDTO> subscribeDto = subscribeService.구독리스트(principalDetails.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보 리스트 가져오기 성공", subscribeDto), HttpStatus.OK);
    }


    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable Long id, @Valid UserUpdateDto userDTO, Errors errors
            , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Map<String, String> errorMap = new HashMap<>();
        if(errors.hasErrors()) {
            for(FieldError error : errors.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                System.out.println("====================");
                System.out.println(error.getDefaultMessage());
                System.out.println("====================");
                System.out.println(errorMap);
            }
            throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
        }

        UserDTO user = userService.update(id, userDTO);
        System.out.println("업데이트됨 >>>>>"+user);

        principalDetails.setUser(userDTOMapper.toEntity(user));

        return new CMRespDto<>(1, "회원수정완료", user);
    }


}
