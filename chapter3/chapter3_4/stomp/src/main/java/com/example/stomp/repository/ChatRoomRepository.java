package com.example.stomp.repository;


import com.example.stomp.domain.room.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


// 방 생성보다 내가 속한 방 목록이 휠씬더 많이 호출된다.
// 그래서 Room Repository는 조회 기능이 핵심이다!
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // 두 사용자 사이의 1:1 방이 있는지 조회
    @Query("""
            select r from ChatRoom r
            where r.isGroup = false
              and r.id in (
                select p1.roomId from Participant p1
                  join Participant p2 on p1.roomId = p2.roomId
                where p1.userId = :userId1
                  and p2.userId = :userId2
              )
            """)
    Optional<ChatRoom> findPrivateRoomBetween(Long userId1, Long userId2);

    // 특정 사용자가 참여 중인 그룹 방 목록
    @Query("""
            select r from ChatRoom r
            where r.isGroup = true
              and r.id in (
                select p.roomId from Participant p
                where p.userId = :userId
              )
            """)
    List<ChatRoom> findGroupRoomsByUserId(Long userId);

    //  전체 방 조회
    @Query("""
            select r from ChatRoom r
            where r.id in (
                select p.roomId from Participant p
                where p.userId = :userId
            )
            """)
    List<ChatRoom> findAllRoomsByUserId(Long userId);


}