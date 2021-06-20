package com.example.photogram.dto;

import com.example.photogram.domain.User;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class SubscribeDTO {
    private User fromUser;
    private User toUser;
}
