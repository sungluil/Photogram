package com.example.photogram.service;

import com.example.photogram.config.PrincipalDetails;
import com.example.photogram.domain.Image;
import com.example.photogram.dto.ImageUploadDto;
import com.example.photogram.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    private String uploadPath = "/Users/cho/Documents/upload/";

    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
        System.out.println("이미지 파일이름 : "+imageFileName);

        Path imageFilePath = Paths.get(uploadPath + imageFileName);

        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        imageRepository.save(image);
    }
}
