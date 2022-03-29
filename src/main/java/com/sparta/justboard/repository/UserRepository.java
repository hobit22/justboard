package com.sparta.justboard.repository;

import com.sparta.justboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByKakaoId(Long kakaoId);

    Optional<User> findByEmail(String kakaoEmail);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}