package com.example.kakaoChat.repository;

import com.example.kakaoChat.relationNtoM.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipatnRepository extends JpaRepository<Participant,Long> {
}
