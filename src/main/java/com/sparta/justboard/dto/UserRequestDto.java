package com.sparta.justboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(regexp="^[a-zA-Z0-9]{3,12}$", message="아이디를 3~12자로 입력해주세요.(특수문자x)")
    private String username;

    @Pattern(regexp="^[a-zA-Z0-9]{4,12}$", message="비밀번호를 4~12자로 입력해주세요.(특수문자x)")
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요")
    private String checkpw;

    @Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message="이메일 형식이 아닙니다.")
    @Email
    private String email;

    private Long kakaoId;

}