package com.example.stomp.controller;

import com.example.stomp.domain.room.ChatRoom;
import com.example.stomp.dto.ChatMessage;
import com.example.stomp.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
// 방 생성, 조회 , 참여, 나가기 등 관리 기능을 담당하는 Rest 컨트롤러 이다!
// ChatRoomController = 관리
// ChatController = 실시간
// 역활을 명확히 분리해야 유지보수가 편해진다.
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 1:1 개인 채팅방 생성 또는 기존 방 조회
    @PostMapping("/room/private/create")
    public ChatRoom createPrivateRoom(@RequestParam Long meId,
                                      @RequestParam Long targetId) {
        return chatRoomService.createPrivateRoom(meId, targetId);
    }

    // 그룹 채팅방 생성
    @PostMapping("/room/group/create")
    public ChatRoom createGroupRoom(@RequestParam String name,
                                    @RequestParam Long ownerId) {
        return chatRoomService.createGroupRoom(name, ownerId);
    }

    // 그룹 채팅방 목록 조회
    @GetMapping("/room/group/list")
    public List<ChatRoom> getGroupRooms(@RequestParam Long userId) {
        return chatRoomService.getGroupRooms(userId);
    }

    // 그룹 채팅방 참여자 추가
    @PostMapping("/room/group/{roomId}/join")
    public void joinGroupRoom(@PathVariable Long roomId,
                              @RequestParam Long userId) {
        chatRoomService.joinGroupRoom(roomId, userId);
    }

    // 이전 메시지 내역 조회
    @GetMapping("/history/{roomId}")
    public List<ChatMessage> getHistory(@PathVariable Long roomId) {
        return chatRoomService.getHistory(roomId);
    }

    // 내 채팅방 목록 조회
    @GetMapping("/my/rooms")
    public List<ChatRoom> getMyRooms(@RequestParam Long userId) {
        return chatRoomService.getMyRooms(userId);
    }

    // 채팅방 나가기
    @PostMapping("/room/group/{roomId}/leave")
    public void leaveRoom(@PathVariable Long roomId,
                          @RequestParam Long userId) {
        chatRoomService.leaveRoom(roomId, userId);
    }

    // 채팅 메시지 읽음 처리
    @PostMapping("/room/{roomId}/read")
    public void markAsRead(@PathVariable Long roomId,
                           @RequestParam Long userId) {
        chatRoomService.markAsRead(roomId, userId);
    }
}