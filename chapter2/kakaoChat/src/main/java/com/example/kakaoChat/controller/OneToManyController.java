package com.example.kakaoChat.controller;

import com.example.kakaoChat.service.OneToManyServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/one-to-many")
public class OneToManyController {

    @Autowired
    private OneToManyServie oneToManyServie;

    @GetMapping("/init")
    public String init(){
        //서비스 호출
        oneToManyServie.createSample();
        return "1:N샘플 생성 완료!";
    }
}
