package com.example.kakaoChat.repository;

import com.example.kakaoChat.relation1to1.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

// 각각의 테이블을 저장,조회,삭제,수정하는 일을 대신 해주는 파일
public interface ProfileRepository extends JpaRepository<Profile,Long> {
}
