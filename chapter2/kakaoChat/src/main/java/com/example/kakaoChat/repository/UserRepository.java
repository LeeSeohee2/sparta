package com.example.kakaoChat.repository;

import com.example.kakaoChat.relation1to1.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
