package com.example.stomp.domain.room;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "chat_room_participant")
@AllArgsConstructor
// 채팅방에 누가 참여하고 있는지를 저장하는 엔티티다
// 참여자 테이블이 없으면 그룹 채팅방을 만들 수 없다!
// Room과 User 사이의 관계를 Participant 연결해준다!
@Builder
public class ChatRoomParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roomId;    // 어떤 방인지
    private Long userId;    // 어떤 유저인지
    private Long joinedAt;  // 언제 들어왔는지

    public ChatRoomParticipant(Long roomId, Long userId, Long joinedAt) {
        this.roomId = roomId;
        this.userId = userId;
        this.joinedAt = joinedAt;
    }


}