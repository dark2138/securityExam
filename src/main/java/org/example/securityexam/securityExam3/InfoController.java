package org.example.securityexam.securityExam3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoController {

    @GetMapping("/user_info")
    public String infodetail() {





        return "user_info";
    }





}
