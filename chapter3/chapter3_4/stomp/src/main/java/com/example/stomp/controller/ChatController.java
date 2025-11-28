package com.example.stomp.controller;

import com.example.stomp.dto.ChatMessage;
import com.example.stomp.websocket.CustomPrincipal;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
// 실시간 메시지를 처리하는 웹소켓/STOMP 전용 컨트롤러다!
// pub/chat/message  로 들어오는 메시지를 받아서 처리하겠다.
// 받은 메시지를 다시 sub/room/{roomId} 브로드 캐스트 해라!
// 실시간 부분만 처리, DB작업은 ChatMessageServie한테 넘겨준다!
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    public ChatController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat/message")
    public void sendMessage(@Payload ChatMessage message,
                            Principal principal) {

        if (principal instanceof CustomPrincipal custom) {
            message.setUserId(custom.getUserId());
        }

        String destination = "/sub/chat/room/" + message.getRoomId();
        messagingTemplate.convertAndSend(destination, message);
    }
}