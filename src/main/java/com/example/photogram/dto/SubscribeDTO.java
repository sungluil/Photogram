package com.example.photogram.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class SubscribeDTO {
    private BigInteger id;
    private String username;
    private String profileImageUrl;
    private Integer subscribeState;
    private Integer equalUserState;

    public SubscribeDTO(BigInteger id, String username, String profileImageUrl, Integer subscribeState, Integer equalUserState) {
        this.id = id;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.subscribeState = subscribeState;
        this.equalUserState = equalUserState;
    }

    public SubscribeDTO() {

    }
}
