package com.example.stomp.websocket;


import java.security.Principal;

//  웹 소켓에서 사용할 나만의 사용자 정보 카드
public class CustomPrincipal implements Principal {

    private final Long userId;

    public CustomPrincipal(Long userId) {
        this.userId = userId;
    }
    // 실제 Principal 유저의 정보를 받아서 문자열로 돌려줘야된다.
    @Override
    public String getName() {
        return String.valueOf(userId);
    }

    // 나중에 메시지 처리할 때 이 메이시지 누가 보낸거야?
    public Long getUserId() {
        return userId;
    }
}
