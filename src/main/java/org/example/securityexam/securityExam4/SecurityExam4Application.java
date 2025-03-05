package org.example.securityexam.securityExam4;

import lombok.extern.slf4j.Slf4j;
import org.example.securityexam.securityExam4.domain.Role;
import org.example.securityexam.securityExam4.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@Slf4j
public class SecurityExam4Application {
    public static void main(String[] args) {
        SpringApplication.run(SecurityExam4Application.class, args);
    }


    @Bean
    public CommandLineRunner run(RoleRepository roleRepository) throws Exception {
       return  args -> {
           if(roleRepository.count() == 0) {
               Role role = new Role();
               role.setName("USER");


               Role role2 = new Role();
               role2.setName("ADMIN");

               roleRepository.saveAll(List.of(role, role2));

               log.info("USER, ADMIN 권한 정보가 추가되었습니다");

           }else {
               log.info("권한 정보가 이미 존재합니다");
           }
           





        };
    }
}
