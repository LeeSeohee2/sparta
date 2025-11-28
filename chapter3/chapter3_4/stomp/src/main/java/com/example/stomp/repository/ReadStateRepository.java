package com.example.stomp.repository;

import com.example.stomp.domain.read.ReadState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReadStateRepository extends JpaRepository<ReadState, Long> {

    Optional<ReadState> findByRoomIdAndUserId(Long roomId, Long userId);
}
