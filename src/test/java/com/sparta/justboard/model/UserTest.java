package com.sparta.justboard.model;

import com.sparta.justboard.dto.UserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @Nested
    @DisplayName("TEST 시작")
    class CreateUser{
        private Long userId;
        private String username;
        private String password;
        private String checkpw;
        private String email;
        private Long kakaoId;

        @BeforeEach
        void setup(){
            userId = 100L;
            username = "test1";
            password = "1234";
            checkpw = "1234";
            email = "hobit22@naver.com";
            kakaoId = 1234L;
        }

        @Test
        @DisplayName("정상 케이스")
        void createUser_Normal(){
            // given
            UserRequestDto requestDto = new UserRequestDto(
                    username,
                    password,
                    checkpw,
                    email,
                    kakaoId
            );

            // when
            User user = new User(requestDto, userId);

            // then
            assertEquals(username, user.getUsername());
            assertEquals(password, user.getPassword());
            assertEquals(email, user.getEmail());
            assertEquals(kakaoId, user.getKakaoId());
        }

        @Nested
        @DisplayName("실패 케이스")
        class FailCases {
            @Nested
            @DisplayName("회원 Id")
            class username {
                @Test
                @DisplayName("null")
                void fail1() {
                    // given
                    userId = null;
                    // given
                    UserRequestDto requestDto = new UserRequestDto(
                            username,
                            password,
                            checkpw,
                            email,
                            kakaoId
                    );

                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        new User(requestDto, userId);
                    });

                    // then
                    assertEquals("회원 Id 가 유효하지 않습니다.", exception.getMessage());
                }

                @Test
                @DisplayName("마이너스")
                void fail2() {
                    // given
                    userId = -100L;

                    UserRequestDto requestDto = new UserRequestDto(
                            username,
                            password,
                            checkpw,
                            email,
                            kakaoId
                    );

                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        new User(requestDto, userId);
                    });

                    // then
                    assertEquals("회원 Id 가 유효하지 않습니다.", exception.getMessage());
                }
            }

            @Nested
            @DisplayName("비밀번호")
            class Password {
                @Test
                @DisplayName("null")
                void fail1() {
                    // given
                    password = null;

                    UserRequestDto requestDto = new UserRequestDto(
                            username,
                            password,
                            checkpw,
                            email,
                            kakaoId
                    );

                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        new User(requestDto, userId);
                    });

                    // then
                    assertEquals("비밀번호가 없습니다.", exception.getMessage());
                }

                @Test
                @DisplayName("빈 문자열")
                void fail2() {
                    // given
                    String password = "";

                    UserRequestDto requestDto = new UserRequestDto(
                            username,
                            password,
                            checkpw,
                            email,
                            kakaoId
                    );

                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        new User(requestDto, userId);
                    });

                    // then
                    assertEquals("비밀번호가 없습니다.", exception.getMessage());
                }
            }
            @Nested
            @DisplayName("비밀번호확인")
            class PasswordCheck {
                @Test
                @DisplayName("null")
                void fail1() {
                    // given
                    checkpw = null;

                    UserRequestDto requestDto = new UserRequestDto(
                            username,
                            password,
                            checkpw,
                            email,
                            kakaoId
                    );

                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        new User(requestDto, userId);
                    });

                    // then
                    assertEquals("비밀번호 확인이 없습니다.", exception.getMessage());
                }

                @Test
                @DisplayName("빈 문자열")
                void fail2() {
                    // given
                    String chekpw = "";

                    UserRequestDto requestDto = new UserRequestDto(
                            username,
                            password,
                            chekpw,
                            email,
                            kakaoId
                    );

                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        new User(requestDto, userId);
                    });

                    // then
                    assertEquals("비밀번호 확인이 없습니다.", exception.getMessage());
                }
            }

            @Nested
            @DisplayName("비밀번호 비밀번호 확인 다름")
            class PasswordNotEqual {
                @Test
                @DisplayName("NotEqual")
                void fail1() {
                    // given
                    String password = "1234";
                    String checkpw = "12345";

                    UserRequestDto requestDto = new UserRequestDto(
                            username,
                            password,
                            checkpw,
                            email,
                            kakaoId
                    );

                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        new User(requestDto, userId);
                    });

                    // then
                    assertEquals("비밀번호가 서로 다릅니다.", exception.getMessage());
                }
            }
/*
            @Nested
            @DisplayName("상품 최저가 페이지 URL")
            class Link {
                @Test
                @DisplayName("null")
                void fail1() {
                    // given
                    link = "https";

                    ProductRequestDto requestDto = new ProductRequestDto(
                            title,
                            image,
                            link,
                            lprice
                    );

                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        new Product(requestDto, userId);
                    });

                    // then
                    assertEquals("상품 최저가 페이지 URL 포맷이 맞지 않습니다.", exception.getMessage());
                }

                @Test
                @DisplayName("URL 포맷 형태가 맞지 않음")
                void fail2() {
                    // given
                    link = "https";

                    ProductRequestDto requestDto = new ProductRequestDto(
                            title,
                            image,
                            link,
                            lprice
                    );

                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        new Product(requestDto, userId);
                    });

                    // then
                    assertEquals("상품 최저가 페이지 URL 포맷이 맞지 않습니다.", exception.getMessage());
                }
            }

            @Nested
            @DisplayName("상품 최저가")
            class LowPrice {
                @Test
                @DisplayName("0")
                void fail1() {
                    // given
                    lprice = 0;

                    ProductRequestDto requestDto = new ProductRequestDto(
                            title,
                            image,
                            link,
                            lprice
                    );

                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        new Product(requestDto, userId);
                    });

                    // then
                    assertEquals("상품 최저가가 0 이하입니다.", exception.getMessage());
                }

                @Test
                @DisplayName("음수")
                void fail2() {
                    // given
                    lprice = -1500;

                    ProductRequestDto requestDto = new ProductRequestDto(
                            title,
                            image,
                            link,
                            lprice
                    );

                    // when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        new Product(requestDto, userId);
                    });

                    // then
                    assertEquals("상품 최저가가 0 이하입니다.", exception.getMessage());
                }
            }

 */
        }
    }

}