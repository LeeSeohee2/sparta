package com.example.kakaoChat.relation1to1;


import com.example.kakaoChat.relation1toN.ChatMessage;
import com.example.kakaoChat.relationNtoM.Participant;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//이걸 DB에 users 테이블로 저장하겠습니다!
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phoneNumber;

    public User(){}

    public User(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @OneToOne(mappedBy = "user")
    private Profile profile;

    public void setProfile(Profile profile) {
        this.profile = profile;
        profile.setUser(this);
    }

    // 1 :N
    //  - 한명이 여러명에게 메시지를 보낼 수있다

    @OneToMany(mappedBy = "sender")
    private List<ChatMessage> messages = new ArrayList<>();


    // 메시지가 자동으로 들어가지 않기 떄문에 추가하는 함수가 필요하다
    public void addMessage(ChatMessage message){
        messages.add(message);
    }

    // N:M
    @OneToMany(mappedBy = "user")
    private List<Participant> participants = new ArrayList<>();

    // 여러개의 채팅방에 참여할 수있다.
    public void addParticipant(Participant participant){
        participants.add(participant);
    }
}
