package com.example.chat.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일을 기준으로 조회
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
