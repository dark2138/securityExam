package org.example.securityexam.beforeSecurity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    public void test(){
      log.info(UserContext.getUser().getName());
    }
}
