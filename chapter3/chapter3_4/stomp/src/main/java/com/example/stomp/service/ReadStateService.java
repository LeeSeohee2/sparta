package com.example.stomp.service;


import com.example.stomp.domain.read.ReadState;
import com.example.stomp.domain.room.ChatRoomParticipant;
import com.example.stomp.dto.ReadStatusResponse;
import com.example.stomp.repository.ChatRoomParticipantRepository;
import com.example.stomp.repository.ReadStateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

// 두가지 기준이 있다.
// updateReadState - 유저가 메시지를 읽었다고 기록
// getReadStatuses - 특정 메시지 기준, 방 참여자 각각이 읽었는지 여부

public class ReadStateService {

    private final ReadStateRepository readStateRepository;
    private final ChatRoomParticipantRepository participantRepository;

    public ReadStateService(ReadStateRepository readStateRepository,
                            ChatRoomParticipantRepository participantRepository) {
        this.readStateRepository = readStateRepository;
        this.participantRepository = participantRepository;
    }

    // 1) 읽음 상태 업데이트
    @Transactional
    public void updateReadState(Long roomId, Long userId, Long messageId) {

        ReadState state = readStateRepository
                .findByRoomIdAndUserId(roomId, userId)
                .orElseGet(() ->
                        new ReadState(roomId, userId, 0L, System.currentTimeMillis())
                );

        state.update(messageId);

        readStateRepository.save(state);
    }

    // 2) 특정 메시지에 대해 방 참여자들의 읽음 여부 조회
    @Transactional(readOnly = true)
    public List<ReadStatusResponse> getReadStatuses(Long roomId, Long messageId) {

        // 해당 방의 참여자 목록
        List<ChatRoomParticipant> participants =
                participantRepository.findByRoomId(roomId);

        return participants.stream()
                .map(p -> {
                    Long userId = p.getUserId();
                    Optional<ReadState> optional =
                            readStateRepository.findByRoomIdAndUserId(roomId, userId);

                    boolean read = optional
                            .map(ReadState::getLastReadMessageId)

//                             프론트에서 어디까지 읽었는지 알고 있는 변수 messageId
                            .orElse(0L) >= messageId;

                    return new ReadStatusResponse(userId, read);
                })
                .collect(Collectors.toList());
    }
}
