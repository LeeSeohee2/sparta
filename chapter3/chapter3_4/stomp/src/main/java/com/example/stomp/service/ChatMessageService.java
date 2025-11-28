package com.example.stomp.service;


import com.example.stomp.domain.message.ChatMessageEntity;
import com.example.stomp.dto.ChatMessage;
import com.example.stomp.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// Stomp로 들어온 메시지를 실제로 DB에 저장하는 핵심 서비스이다!
// stomp 메시지 전달만 해준다.
public class ChatMessageService {

    // 메시지를 처리하는 DB연결 코드
    // 1) 채팅메시지를 관리하는 JPA 관리자 생성하기
    private final ChatMessageRepository repository;

    // 2) 생성자를 이용해서 스프링이 직접 주입하도록 하는게 안전하다!
    public ChatMessageService(ChatMessageRepository repository) {
        this.repository = repository;
    }

     //3) 데이터베이스에 저장을 하다가 에러가 발생하면 초기화한다.!
    @Transactional
    public void save(ChatMessage dto) {

        //4) DTO 객체 -> DB용 객체 변한다.
        ChatMessageEntity entity = new ChatMessageEntity(
                dto.getRoomId(),
                dto.getUserId(),
                dto.getContent(),
                System.currentTimeMillis()
        );

        //5) 저장하기
        repository.save(entity);
    }
}