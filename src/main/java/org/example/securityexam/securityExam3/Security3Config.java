package org.example.securityexam.securityExam3;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootConfiguration
@Configuration
@EnableWebSecurity
public class Security3Config {

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/hello/**", "/shop/**", "/user_info", "/static/**" , "/js/**", "/css/**").permitAll()
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/abc").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERUSER")
                        .anyRequest().authenticated()
                  )
                .formLogin(Customizer.withDefaults())
                .logout(log -> log
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/hello")
                );
        return http.build();
    }



    // USER, ADMIN , SUPERUSER --> 세개의 role이 존재한다고 가정!!
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
       UserDetails user = User.withUsername("user")
               .password(passwordEncoder.encode("8888"))
               .roles("USER")
               .build();

        UserDetails user2 = User.withUsername("sangwon")
                .password(passwordEncoder.encode("1212"))
                .roles("USER", "ADMIN")
                .build();

        UserDetails user3 = User.withUsername("admin")
                .password(passwordEncoder.encode("9999"))
                .roles("ADMIN")
                .build();

        UserDetails user4 = User.withUsername("superuser")
                .password(passwordEncoder.encode("9292"))
                .roles("SUPERUSER")
                .build();

        return new InMemoryUserDetailsManager(user, user2, user3, user4);
    }

    //UserDetailsService는 웹사이트에서 누가 누구인지,
    // 무엇을 할 수 있는지를 관리하는 중요한 역할을 해요.
    // 마치 학교에서 학생부가 하는 일과 비슷하다고 생각하면 됩니다!

    // 패스워드는 암호화 해서 들어와야 함 >>> 인코딩
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





}
