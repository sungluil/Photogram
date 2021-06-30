package com.example.photogram.dto;

import lombok.Data;

@Data
public class SubscribeDTO {
    private int id;
    private String username;
    private String profileImageUrl;
    private Integer subscribeState;
    private Integer equalUserState;
}
