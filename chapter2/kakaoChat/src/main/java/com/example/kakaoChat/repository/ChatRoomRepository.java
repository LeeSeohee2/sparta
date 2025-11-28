package com.example.kakaoChat.repository;

import com.example.kakaoChat.relationNtoM.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
}
