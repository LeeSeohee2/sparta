package com.example.stomp.domain.room;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "chat_room")
@AllArgsConstructor
@Builder

//채팅방 자체를 나타내는 엔티티다!
// 방 이름, 생성시각, 1:1인지 그룹인지 여부를 방장이니?
//  대화 메시지와 채팅방은 절대로 한 엔티티에 섞으면 안된다.
// 채팅방 정보는 방 정보대로, 메시지는 메시지대로 분리해야 확장성이 좋다.
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 방 제목
    private Long createdAt;     // 생성 시각
    private boolean isGroup;    // 그룹이면 true, 1:1 false
    private Long ownerId;       // 방장님



}