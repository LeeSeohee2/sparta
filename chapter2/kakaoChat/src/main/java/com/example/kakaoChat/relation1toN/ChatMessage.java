package com.example.kakaoChat.relation1toN;

import com.example.kakaoChat.relation1to1.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;
    private LocalDateTime timestamp;

    public ChatMessage() {}
    public ChatMessage(String content, User sender) {
        this.content = content;
        this.sender = sender;
        this.timestamp = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

}
