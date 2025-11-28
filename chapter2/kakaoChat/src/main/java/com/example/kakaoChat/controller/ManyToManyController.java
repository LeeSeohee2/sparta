package com.example.kakaoChat.controller;

import com.example.kakaoChat.relation1to1.User;
import com.example.kakaoChat.relationNtoM.ChatRoom;
import com.example.kakaoChat.service.ManyToManyServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/many-to-many")
public class ManyToManyController {

    @Autowired
    private ManyToManyServie manyToManyServie;

    public User createUser(
            @RequestParam String name,
            @RequestParam String phoneNumber
    ) {
        return manyToManyServie.createUser(name, phoneNumber);
    }

    @PostMapping("/room")
    public ChatRoom createRoom(@RequestParam String roomName) {
        return manyToManyServie.createRoom(roomName);
    }

    @PostMapping("/join")
    public String join(
            @RequestParam Long userId,
            @RequestParam Long roomId
    ) {
        return manyToManyServie.joinRoom(userId, roomId);
    }

}
