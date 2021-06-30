package com.example.photogram.controller;

import com.example.photogram.config.PrincipalDetails;
import com.example.photogram.domain.Image;
import com.example.photogram.dto.CMRespDto;
import com.example.photogram.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final ImageService imageService;

    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PageableDefault(size = 3) Pageable pageable ) {

        Page<Image> images =  imageService.이미지스토리(principalDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(new CMRespDto<>(1, "성공", images), HttpStatus.OK);
    }
}
