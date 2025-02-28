package org.example.securityexam.securityExam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @GetMapping("/info")
    public String info() {
        return "Info";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "LoginForm page";
    }

    @GetMapping("/success")
    public String success() {
        return "Success";
    }

    @GetMapping("/fail")
    public String fail() {
        return "Fail";
    }


}
