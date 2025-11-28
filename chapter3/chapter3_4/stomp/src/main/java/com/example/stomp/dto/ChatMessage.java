package com.example.stomp.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
// Stomp가 송/수신되는 핵심 DTO
public class ChatMessage {
    private Long id;
    //1) 어떤 종류의 메시지인지?
    //  - 채팅 시스템이 메시지를 어떻게 처리해야하는지 판단을 해야된다.구분자!
    public enum  MessageType
    {
        ENTER, //2) 누군가 채팅방에 들어온 메시지
        TALK,  //3) 일반 채팅 메시지
        LEVAE  //4) 누군가 나간 메세지
    }

    private MessageType type;

    // 몇번 방이니? 서버는 이 값을보고 어떤 채팅방(sub주소)로 메시지를 보내야될 지 결정한다.
    private Long roomId;

    // 누가 보냈니?
    private Long userId;

    // 채팅메시지 텍스트 저장
    // TALK 경우는 반드시 있다.
    private String content;

    private Long createdAt;


}
/*
클라이언트에서 서버로 보낼때
stomp.send("/pub/chat.send", {}, JSON.stringify({
  type: "TALK",
  roomId: 1,
  userId: 10,
  content: "헬로!"
}));

서버에서 클라이언트로 보낼떄
{
  "type": "TALK",
  "roomId": 1,
  "userId": 10,
  "content": "헬로!"
}
이걸 그대로 브로커가 구독자들에게 전송을 한다.

 */

