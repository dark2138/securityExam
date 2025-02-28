package org.example.securityexam.securityExam3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HelloService {

    public void userLog() {

        String msg =null;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && auth.isAuthenticated()) {
            msg =  "유저가 없습니다.";
            log.info(msg);
        }
        Object principal = auth.getPrincipal();
        if(principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            msg = "현재 사용자는 " +userDetails.getUsername();

        }else{
            msg = "현재 사용자는 " +  principal.toString();
        }

        log.info(msg);

    }

}
