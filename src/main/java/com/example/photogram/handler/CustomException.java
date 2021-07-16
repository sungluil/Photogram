package com.example.photogram.handler;

import javassist.SerialVersionUID;

public class CustomException extends RuntimeException{

    private static final Long SerialVersionUID = 1L;

    public CustomException(String message) {
        super(message);
    }
}
