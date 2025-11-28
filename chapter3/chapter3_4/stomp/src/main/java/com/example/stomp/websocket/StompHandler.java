package com.example.stomp.websocket;

import com.example.stomp.security.JwtTokenProvider;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;

@Component
public class StompHandler implements ChannelInterceptor {
    //1) jwt 인증 관련 확인
    private final JwtTokenProvider jwtTokenProvider;

    public StompHandler(JwtTokenProvider jwtTokenProvider) {

        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 채팅방 입장할 때 토큰 검사하고 그 토큰 속 userId websocket 사용자 정보에
    // 넣는 과정

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("ddd");
        // 지금 메시지를 안에 stomp 정보가 있으면 그걸 꺼내기 위해서 래핑한다.
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        System.out.println("StompHeaderAccessor accessor = " + accessor);
        //2) 입장할때만 검사하겠다! 웹 소켓 연결 시도(Connect) 인지 확인하는 부분
        if(StompCommand.CONNECT.equals(accessor.getCommand())) {
            //3) 프론트에서 Authorization 헤더에서 토큰을꺼내기
            String token = accessor.getFirstNativeHeader("Authorization");
            System.out.println("222");
            //4) 토큰이 있다면 실행해라
            if (token != null) {
                token = token.replace("Bearer ", "");
                //5) 이 토큰이 누구 것인지 확인해서 토큰 속 userId를 꺼낸다.
                Long userId = jwtTokenProvider.getUserId(token);
                System.out.println("333");
                //6) 서버는 어떤 사용자가 구독했는지 , 누가 보낸 메시지인지 알
                //   수 있게 된다.
                accessor.setUser(new CustomPrincipal((userId)));


            }
        }
        System.out.println("$44");
        //7) 중간에서 인터샙터 했기 때문에 다음 단계로 보내라!
        return message;
    }
}
