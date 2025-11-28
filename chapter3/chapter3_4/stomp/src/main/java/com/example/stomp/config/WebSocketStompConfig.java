package com.example.stomp.config;

import com.example.stomp.websocket.StompHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
// 우리 프로젝트에서 실시간 메시징 사용할꺼야 활성화!
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler  stompHandler;
    public WebSocketStompConfig(StompHandler stompHandler) {
        this.stompHandler = stompHandler;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat")
                .setAllowedOriginPatterns("*") // 스프링3 버전에서 지원하는 CORS 해제법!
                .withSockJS(); // 웹소켓이 지원안되는 환경에서 접속을 해야될때
    }
    //  Stomp의 메시지를 보내고 받을려면 브로커 아웃바운드 설정이 없다!
    //  메시지 아예 동작 안함!
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");// 구독 주소
        registry.setApplicationDestinationPrefixes("/pub"); // 발행 주소
    }

    //StompHandler(인증 인터샙터)를 메시지 채널에 등록
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }

}
