package org.example.securityexam.securityExam4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @GetMapping("/aaa")
    public String aaa() {
        return "exam4/users/shop";
    }

}
