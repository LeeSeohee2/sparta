package com.example.kakaoChat.relationNtoM;

import com.example.kakaoChat.relation1to1.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 유저인지? 어떤 방 인지 두 정보가 반드시 있어야된다
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    // --- Setter (양방향용) ---
    public void setUser(User user) {
        this.user = user;
    }

    public void setRoom(ChatRoom room) {
        this.chatRoom = room;
    }
}
