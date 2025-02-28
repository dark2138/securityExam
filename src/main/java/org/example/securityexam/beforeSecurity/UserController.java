package org.example.securityexam.beforeSecurity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        log.info("hello() 실행 " + Thread.currentThread().getName());

        Thread.sleep(2000);

        userService.test();

        return "hello" + UserContext.getUser().getName();
    }
    // 하나의 요청은 하나의 쓰레드가 처리

    @GetMapping("/test/hi")
    public String hi() throws InterruptedException{
        log.info("hi() 실행");


        Thread.sleep(2000);
        return "hi";
    }


}
