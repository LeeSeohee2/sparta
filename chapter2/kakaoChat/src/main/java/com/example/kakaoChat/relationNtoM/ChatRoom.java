package com.example.kakaoChat.relationNtoM;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_rooms")
@Getter
@Setter
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;

    //채팅방 입장에서는 여러명의 참여자가 있을 수 있다.
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<Participant>  participants = new ArrayList<>();


    public void addParticipant(Participant participant) {
        participants.add(participant);
        participant.setRoom(this);  // 양방향 연결
    }
}
