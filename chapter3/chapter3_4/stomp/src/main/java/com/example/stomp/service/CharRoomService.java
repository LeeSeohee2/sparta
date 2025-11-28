package com.example.stomp.service;


import com.example.stomp.domain.room.ChatRoom;
import com.example.stomp.domain.room.ChatRoomParticipant;
import com.example.stomp.dto.ChatMessage;
import com.example.stomp.repository.ChatMessageRepository;
import com.example.stomp.repository.ChatRoomParticipantRepository;
import com.example.stomp.repository.ChatRoomRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
//1:1 -> 1:N으로 확장을 담당하는 핵심 비즈니스 로직이다!
// RoomService = 방 관련된 모든 비지니스 로직의 중심이다!
// Stomp메시지만!
// Rest Api는 방에 대한 모든 관리 기능을 제공한다.
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomParticipantRepository participantRepository;
    private final ChatMessageService chatMessageService;

    // 1:1 개인 채팅방 생성 또는 기존 방 조회
    public ChatRoom createPrivateRoom(Long meId, Long targetId) {
        return chatRoomRepository.findPrivateRoomBetween(meId, targetId)
                .orElseGet(() -> {
                    ChatRoom room = ChatRoom.builder()
                            .name("private") // 필요시 동적으로 변경
                            .isGroup(false)
                            .ownerId(meId)
                            .build();
                    chatRoomRepository.save(room);

                    participantRepository.save(ChatRoomParticipant.builder()
                            .roomId(room.getId())
                            .userId(meId)
                            .build());

                    participantRepository.save(ChatRoomParticipant.builder()
                            .roomId(room.getId())
                            .userId(targetId)
                            .build());

                    return room;
                });
    }

    // 그룹 채팅방 생성
    public ChatRoom createGroupRoom(String name, Long ownerId) {
        ChatRoom room = ChatRoom.builder()
                .name(name)
                .isGroup(true)
                .ownerId(ownerId)
                .build();
        chatRoomRepository.save(room);

        // 방장은 기본 참여자로 추가
        participantRepository.save(ChatRoomParticipant.builder()
                .roomId(room.getId())
                .userId(ownerId)
                .build());

        return room;
    }

    // 그룹 채팅방 목록 조회
    @Transactional(readOnly = true)
    public List<ChatRoom> getGroupRooms(Long userId) {
        return chatRoomRepository.findGroupRoomsByUserId(userId);
    }

    // 그룹 채팅방 참여자 추가
    public void joinGroupRoom(Long roomId, Long userId) {
        participantRepository.save(
                ChatRoomParticipant.builder()
                        .roomId(roomId)
                        .userId(userId)
                        .build()
        );
    }

    // 이전 메시지 내역 조회 (4-1 메시지 서비스에 위임)
    @Transactional(readOnly = true)
    public List<ChatMessage> getHistory(Long roomId) {
        return chatMessageService.getHistory(roomId);
    }

    // 내 채팅방 목록 조회 (1:1 + 그룹)
    @Transactional(readOnly = true)
    public List<ChatRoom> getMyRooms(Long userId) {
        return chatRoomRepository.findAllRoomsByUserId(userId);
    }

    // 채팅방 나가기
    public void leaveRoom(Long roomId, Long userId) {
        participantRepository.deleteByRoomIdAndUserId(roomId, userId);
    }

    // 채팅 메시지 읽음 처리 (4-2/4-3 확장 포인트)
    public void markAsRead(Long roomId, Long userId) {
        // 나중에 ReadStateEntity 추가 후 여기에서 처리
        System.out.println("[READ] roomId=" + roomId + ", userId=" + userId);
    }
}