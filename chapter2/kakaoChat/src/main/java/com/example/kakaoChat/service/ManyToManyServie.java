package com.example.kakaoChat.service;

import com.example.kakaoChat.relation1to1.User;
import com.example.kakaoChat.relationNtoM.ChatRoom;
import com.example.kakaoChat.relationNtoM.Participant;
import com.example.kakaoChat.repository.ChatRoomRepository;
import com.example.kakaoChat.repository.ParticipatnRepository;
import com.example.kakaoChat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManyToManyServie {

    private final ParticipatnRepository participantRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    //1) 유저 생성
    public User createUser(String name, String phoneNumber) {
        User user = new User(name, phoneNumber);
        return userRepository.save(user);
    }

    //2) 방 생성
    public ChatRoom createRoom(String roomName) {
        ChatRoom room = new ChatRoom();
        room.setRoomName(roomName);
        return chatRoomRepository.save(room);
    }

    // 3) 양뱡향 연결을 해야된다.
    //   여러개의 채팅방과 여러명의 유저가 들어올 수있다.
    //   채팅방이 없거나 유저가 없을 수있다.
    public String joinRoom(Long userId, Long roomId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방 없음"));

        Participant p = new Participant();

        user.addParticipant(p);
        room.addParticipant(p);

        participantRepository.save(p);

        return "참여 완료";
    }

}
