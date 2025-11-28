package com.example.stomp.domain.message;

import com.example.stomp.dto.ChatMessage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_message")
@Getter
@NoArgsConstructor
@Setter
// 메시지를 DB에 저장하는 정보
// 실시간 메시지는 날아가면 끝이다 우리는 DB에 남겨야된다.
// 대화방을 다시 들어왔을 때 이력을 볼 수있다.
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roomId;    // 어떤 방인지
    private Long userId;    // 누가 보냈는지

    @Column(columnDefinition = "TEXT")
    private String content; // 메시지 내용

    private Long createdAt; // 밀리초 타임스탬프

    public ChatMessageEntity(Long roomId, Long userId,
                             String content, Long createdAt) {
        this.roomId = roomId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }
    // DTO -> Entity 어디서 변환할 지? 고민 !

    //  Entity → DTO 변환 정확히 맞춘 버전
    public ChatMessage toDto() {
        ChatMessage dto = new ChatMessage();
        dto.setId(id);
        dto.setRoomId(roomId);
        dto.setUserId(userId);      // ★ senderId → userId 로 수정
        dto.setContent(content);
        dto.setCreatedAt(createdAt);
        return dto;
    }
}
