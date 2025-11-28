package com.example.kakaoChat.repository;

import com.example.kakaoChat.relation1toN.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
}
