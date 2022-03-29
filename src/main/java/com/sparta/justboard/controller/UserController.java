package com.sparta.justboard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.justboard.dto.SignupRequestDto;
import com.sparta.justboard.service.KakaoUserService;
import com.sparta.justboard.service.UserService;
import com.sparta.justboard.util.CheckEmailValidator;
import com.sparta.justboard.util.CheckPasswordValidator;
import com.sparta.justboard.util.CheckUsernameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;
    private final CheckUsernameValidator checkUsernameValidator;
    private final CheckPasswordValidator checkPasswordValidator;
    private final CheckEmailValidator checkEmailValidator;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkUsernameValidator);
        binder.addValidators(checkPasswordValidator);
        binder.addValidators(checkEmailValidator);
    }

    @Autowired
    public UserController(UserService userService, KakaoUserService kakaoUserService, CheckUsernameValidator checkUsernameValidator, CheckEmailValidator checkEmailValidator, CheckPasswordValidator checkPasswordValidator) {
        this.userService = userService;
        this.kakaoUserService = kakaoUserService;
        this.checkEmailValidator = checkEmailValidator;
        this.checkPasswordValidator = checkPasswordValidator;
        this.checkUsernameValidator = checkUsernameValidator;
    }

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(@Valid SignupRequestDto requestDto, Errors errors, Model model) {
        if (errors.hasErrors()) { /* 회원가입 실패시 입력 데이터 값을 유지 */
            model.addAttribute("requestDto", requestDto); /* 유효성 통과 못한 필드와 메시지를 핸들링 */
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                System.out.println("key = " + key);
                System.out.println("message = " + validatorResult.get(key));
                model.addAttribute(key, validatorResult.get(key));
            } /* 회원가입 페이지로 다시 리턴 */
            return "signup";
        }
        userService.registerUser(requestDto);
        return "redirect:/user/login";
    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        kakaoUserService.kakaoLogin(code);

        return "redirect:/";
    }
}