package com.example.stomp.domain.read;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "read_state")
public class ReadState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 방인지
    private Long roomId;

    // 어떤 유저인지
    private Long userId;

    // 이 유저가 이 방에서 마지막으로 읽은 메시지 ID
    private Long lastReadMessageId;

    private Long updatedAt;

    public ReadState(Long roomId, Long userId, Long lastReadMessageId, Long updatedAt) {
        this.roomId = roomId;
        this.userId = userId;
        this.lastReadMessageId = lastReadMessageId;
        this.updatedAt = updatedAt;
    }

    // 메시지를 더 앞까지 읽었을 때만 업데이트
    public void update(Long messageId) {
        if (this.lastReadMessageId == null || messageId > this.lastReadMessageId) {
            this.lastReadMessageId = messageId;
            this.updatedAt = System.currentTimeMillis();
        }
    }
}