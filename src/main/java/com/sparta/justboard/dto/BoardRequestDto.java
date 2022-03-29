package com.sparta.justboard.dto;

import com.sparta.justboard.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {
    private String title;
    private String text;
    private User user;
}