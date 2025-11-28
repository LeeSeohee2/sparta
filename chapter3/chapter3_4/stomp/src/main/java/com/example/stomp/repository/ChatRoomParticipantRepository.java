package com.example.stomp.repository;

import com.example.stomp.domain.room.ChatRoomParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//참여자 테이블이 없으면 그룹 방 관리가 불가능하다.
// 참여자 관리 전용 리포지토리
public interface ChatRoomParticipantRepository extends JpaRepository<ChatRoomParticipant, Long>{

    // 참여자 삭제, 저장( 초대, 삭제)
    // 방에 들어가고 나가는 사람들에 대한 내용들을 알아야된다.
    // 방나가기 , 강퇴기능, 유저가 앱삭제후 재접속 시 재 초대 가능
    void deleteByRoomIdAndUserId(Long roomId, Long userId);

    List<ChatRoomParticipant> findByRoomId(Long roomId);
}
