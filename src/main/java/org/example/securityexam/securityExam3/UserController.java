package org.example.securityexam.securityExam3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @GetMapping("/aaa")
    public String aaa() {
        return "aaa";
    }

    @GetMapping("/bbb")
    public String bbb() {
        return "bbb";
    }

    @GetMapping("/ccc")
    public String ccc() {
        return "ccc";
    }

    @GetMapping("/profile")
    public String userProfile() {
        return "This is the user profile. Only USER and ADMIN roles can access this.";
    }
}
