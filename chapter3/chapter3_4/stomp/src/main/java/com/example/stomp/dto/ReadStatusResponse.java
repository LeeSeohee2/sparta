package com.example.stomp.dto;


// 메시지별 읽음 여부 응답 DTO

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReadStatusResponse {

    private Long userId;
    private boolean read;  // true면 읽음, false면 안 읽음
}

