package com.example.kakaoChat.service;

import com.example.kakaoChat.relation1to1.User;
import com.example.kakaoChat.relation1toN.ChatMessage;
import com.example.kakaoChat.repository.ChatMessageRepository;
import com.example.kakaoChat.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class OneToManyServie {

    //데이터베이스 처리를 하려면 Jpa들을 가져오기
    public final UserRepository userRepository;
    public final ChatMessageRepository chatMessageRepository;

    public OneToManyServie(UserRepository userRepository,
                           ChatMessageRepository chatMessageRepository) {
        this.userRepository = userRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    // 샘플 함수
    public void createSample(){
        // 1) 유저 생성
        User user = new User("김철수","010-1234-1234");

        //2)유저 추가
        userRepository.save(user);

        //3) 메시지 생성(보낸 사람 설정)
        ChatMessage m1 = new ChatMessage("안녕하세요",user);
        ChatMessage m2 = new ChatMessage("에리나 뭐해?",user);
        ChatMessage m3 = new ChatMessage("피곤해?",user);

        //4)  user-> message 연결
        user.addMessage(m1);
        user.addMessage(m2);
        user.addMessage(m3);

        //5) 실제 디비에 기록!
        chatMessageRepository.save(m1);
        chatMessageRepository.save(m2);
        chatMessageRepository.save(m3);


    }

}
