package com.example.stomp.controller;


// Rest로 읽음 처리/ 읽음 상태를 조회 API

import com.example.stomp.dto.ReadStatusResponse;
import com.example.stomp.dto.ReadUpdateRequest;
import com.example.stomp.service.ReadStateService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/read")
public class ReadStateController {

    private final ReadStateService readStateService;

    public ReadStateController(ReadStateService readStateService) {
        this.readStateService = readStateService;
    }

    // 1) 내가 이 방에서 messageId까지 읽었다고 서버에 알리는 API
    @PostMapping("/rooms/{roomId}")
    public void updateRead(@PathVariable Long roomId,
                           @RequestBody ReadUpdateRequest request,
                           Principal principal) {

        // 실무에서는 JWT에서 userId를 꺼낸다.
        Long userId = Long.valueOf(principal.getName());

        readStateService.updateReadState(roomId, userId, request.getMessageId());
    }

    // 2) 특정 메시지에 대해 방 참여자들의 읽음 여부 조회 API
    @GetMapping("/rooms/{roomId}/messages/{messageId}")
    public List<ReadStatusResponse> getReadStatuses(
            @PathVariable Long roomId,
            @PathVariable Long messageId) {

        return readStateService.getReadStatuses(roomId, messageId);
    }
}