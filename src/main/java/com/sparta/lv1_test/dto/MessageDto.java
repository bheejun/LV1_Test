package com.sparta.lv1_test.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "set")
public class MessageDto<T> {
    private String message;
    private int statusCode;
    private T data;

    public static <T> MessageDto<T> setSuccess(String message, T data) {
        return MessageDto.set(message, 200, data);
    }

    public static <T> MessageDto<T> setFailed(String message) {
        return MessageDto.set(message, 400, null);
    }

}
