package com.example.jwt.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    // 우리는 이멜일로 중복 검사한다.
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

}
