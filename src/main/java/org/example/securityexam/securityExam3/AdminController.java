package org.example.securityexam.securityExam3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {


    @GetMapping("/abc")
    public String adminAbc() {
        return "This is the admin ABC page. Only ADMIN role can access this.";
    }

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "This is the admin dashboard. ADMIN and SUPURUSER roles can access this.";
    }

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



}
