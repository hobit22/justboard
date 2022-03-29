package com.sparta.justboard.dto;


import com.sparta.justboard.model.Board;
import com.sparta.justboard.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
    private String text;
    private User user;
    private Board board;
}