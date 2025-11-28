package com.example.stomp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 읽음 상태 업데이트 요청

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 유저가 이 메시지까지 읽었다 요청 DTO
public class ReadUpdateRequest {

    private Long roomId;    // 방 ID
    private Long userId;    // 유저 ID
    private Long messageId; // 이 메시지까지 읽었다
}
