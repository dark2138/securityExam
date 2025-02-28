package org.example.securityexam.securityExam3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/shop")
public class ShopController {

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

    @GetMapping("/items")
    public String shopItems() {
        return "Here are the shop items. This is also a public endpoint.";
    }
}
