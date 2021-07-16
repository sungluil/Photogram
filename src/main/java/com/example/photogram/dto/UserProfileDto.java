package com.example.photogram.dto;

import com.example.photogram.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
    private boolean pageOwnerState;
    private int imageCount;
    private User user;
}