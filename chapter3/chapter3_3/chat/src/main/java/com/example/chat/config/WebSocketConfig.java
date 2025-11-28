package com.example.chat.config;

import com.example.chat.handler.ChatHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
// 어떤 경로를 받을지 어떤 핸들러에 연결 할지 정의하는 클래스!( 환경설정 )

@EnableWebSocket
// 어노테이션 꼭 붙이기!
// 이 프로젝트는 웹 소켓 사용하겠습니다! 활성화!
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    public WebSocketConfig(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //1) react에서 요청하는 엔드포인터로 웹 소켓을 연결하겠습니다.
        registry.addHandler(chatHandler,"/ws/chat")
                .setAllowedOrigins("*");
    }
}
