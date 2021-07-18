package com.example.photogram.service;

import com.example.photogram.config.PrincipalDetails;
import com.example.photogram.domain.Image;
import com.example.photogram.dto.ImageUploadDto;
import com.example.photogram.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private String uploadFolder = "/Users/cho/Documents/upload/";

    @Transactional(readOnly = true) // 영속성 컨텍스트 변경 감지를 해서, 더티체킹, flush(반영) X
    public Page<Image> 이미지스토리(Long principalId, Pageable pageable){
        Page<Image> images = imageRepository.mStory(principalId, pageable);
        return images;
    }

    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {

        /**
         * 업로드 전략
         * 1. 원본파일명 + 범용 고유 식별자로 저장할 파일명 생성
         * 2. Paths.get()을 이용해 파일경로 + 파일이름으로 이미지저장경로 설정
         * 3. Files.write 이용 파일Path 파일 getBytes()
         * 4. 이미지테이블에 저장
         * 5. 즉 내가설정한경로에 파일떨구고 그파일정보들중에 원하는걸 테이블에 인서트
         */

        UUID uuid = UUID.randomUUID(); // uuid
        String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); // 1.jpg
        System.out.println("이미지 파일이름 : "+imageFileName);

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        // 통신, I/O -> 예외가 발생할 수 있다.
        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser()); // 5cf6237d-c404-43e5-836b-e55413ed0e49_bag.jpeg
        imageRepository.save(image);
    }
}
