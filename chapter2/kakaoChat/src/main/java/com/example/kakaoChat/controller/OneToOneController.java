package com.example.kakaoChat.controller;

import com.example.kakaoChat.service.OneToOneService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/one-to-one")
public class OneToOneController {

    private final OneToOneService oneToOneService;

    public OneToOneController(OneToOneService oneToOneService) {
        this.oneToOneService = oneToOneService;
    }

    @GetMapping("/init")
    public String oneToOne() {
        oneToOneService.createSample();

        return "one-to-one";
    }
}
//실행순서
// 1. /init 들어온다
// 2. service 호출된다
// 3. createSample() 호출된다.
// 4. 각각 객체 만들고 데이터베이스(Jpa repository가 저장해준다.
