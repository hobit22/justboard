package com.sparta.justboard.validator;

import com.sparta.justboard.dto.UserRequestDto;
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserValidator {

    public static void validateUserInput(UserRequestDto requestDto, Long userId) {
        // 입력값 Validation
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("회원 Id 가 유효하지 않습니다.");
        }

        if (requestDto.getUsername() == null || requestDto.getUsername().isEmpty()) {
            throw new IllegalArgumentException("유저명이 없습니다.");
        }

        if (requestDto.getPassword() ==null || requestDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("비밀번호가 없습니다.");
        }

        if (requestDto.getCheckpw() ==null || requestDto.getCheckpw().isEmpty()) {
            throw new IllegalArgumentException("비밀번호 확인이 없습니다.");
        }

        if (!Objects.equals(requestDto.getPassword(), requestDto.getCheckpw())) {
            throw new IllegalArgumentException("비밀번호가 서로 다릅니다.");
        }
    }
}
