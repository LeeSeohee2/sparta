package com.example.kakaoChat.relation1to1;

import jakarta.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //고유한 순서
    private Long id;

    private String nickname;
    private String statusMessage;

    // 연결할 컬럼명을 JPA에서는 변수명으로 쓴다.
    // 1:1 어노테이션
    // 사람도 프로필을 하나만 가질 수있다.
    // 프로필은 한 사람에게만 연결된다
    @OneToOne
    @JoinColumn(name = "user_id",unique = true)
    private User user;

    public Profile() {}
    public Profile(String nickname, String statusMessage) {
        this.nickname = nickname;
        this.statusMessage = statusMessage;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
