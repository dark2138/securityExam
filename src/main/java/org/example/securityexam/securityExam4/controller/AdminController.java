package org.example.securityexam.securityExam4.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {


    @GetMapping("/aaa")
    public String aaa() {
        return "exam4/users/admingood";
    }

}
