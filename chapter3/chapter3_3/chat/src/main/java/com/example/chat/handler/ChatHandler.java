package com.example.chat.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

// 채팅 메시지를 관리하는 클래스!
// TextWebSocketHandler 메시지를 처리하는 핵심 엔진이다!
// - 문자열 기반 메시지를 처리할 수있다.
// 브라우저가 메시지를 보내면 여기로 들어온다. 서버가 브라우저로 메시지를 보내는 것도 여기서
// 이루어진다.

@Component
public class ChatHandler extends TextWebSocketHandler {

    // 대화방에 접속한 손님 리스트에 저장한다.
    // WebSocketSession 대화방에 손님 한명!
    private final List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    // 손님이 카카오톡 오픈 채팅방에 "입장했습니다" 하고 똑같은 개념이다!
    // 웹소켓은 연결이 맺어지는 순간 자동으로 이 메서드가 실행된다.
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println(" 클라이언트 접속: " + session.getId());
    }

    //메시지를 보내면 가장 먼저 실행되는 핵심 메서드!


    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) throws Exception {

        // 이거 부분이 약간 웹 소켓의 심장!
        // 서버는 그 메시지를 현재 접속한 모든 사람에게 다시 보내줍니다.
        // message.getPayload() == 실제 전송된 텍스트(JSON)
        // 브로드 캐스트 구조다! (모든 채팅방 사용자에게 그대로 메시지를 보낸다.)
        System.out.println("수신 메시지: " + message.getPayload());

        for(WebSocketSession s : sessions){
            s.sendMessage(new TextMessage(message.getPayload()));
        }
        // 포스트맨으로 보내기
        session.sendMessage(new  TextMessage("서버가 받음: "
                                + message.getPayload() +" 졸려! "));
    }

    // 채팅방에서 손님이 유저가 나가면 리스트에서 제거한다.


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("클라이언트 종료:" + session.getId());
    }
}
