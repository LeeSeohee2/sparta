package com.example.stomp.repository;

import com.example.stomp.domain.message.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository
        extends JpaRepository<ChatMessageEntity, Long> {

    List<ChatMessageEntity> findByRoomIdOrderByIdAsc(Long roomId);
}
