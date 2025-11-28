package com.example.kakaoChat.service;


import com.example.kakaoChat.relation1to1.Profile;
import com.example.kakaoChat.relation1to1.User;
import com.example.kakaoChat.repository.ProfileRepository;
import com.example.kakaoChat.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class OneToOneService {

    //1. JPA 전담 직원
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    // 2. final 고정된 값으로 초기화를 해야된다.
    //    그래서 생성자를 이용해서 처리한다!
    public OneToOneService(UserRepository userRepository,
                           ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }
    //3. 직접 데이터 저장하기
    public void createSample(){
        // -1) 유저 생성
        User user = new User("에리나","010-1234-1234");
        userRepository.save(user);

        // -2) 프로필 생성 및 연결
        Profile profile = new Profile("empress","아우..배고파");
        user.setProfile(profile);
        profileRepository.save(profile);

    }

}
