package org.example.securityexam.securityExam4.config;


import lombok.RequiredArgsConstructor;
import org.example.securityexam.securityExam4.security.CustomUserDetailservice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailservice customUserDetailservice;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 암호화 사용
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomUserDetailservice customUserDetailservice) throws Exception {
        http
                // CSRF 보호 비활성화 (테스트용)
                .csrf(csrf -> csrf.disable())

                // 경로별 접근 제어 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup", "/register123", "/error", "/loginform" ,"/myinfo" , "/").permitAll() // GET 요청 허용
                        //.requestMatchers(HttpMethod.POST, "/register123").permitAll()
                        .requestMatchers("/welcome", "/shop/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )

                // 로그인 설정
                .formLogin(form -> form
                        .loginPage("/loginform") // 커스텀 로그인 페이지 경로
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/welcome") // 로그인 성공 시 리다이렉트 경로
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 URL 설정
                        .logoutSuccessUrl("/loginform") // 로그아웃 성공 시 리다이렉트 경로
                )
                .userDetailsService(customUserDetailservice); // UserDetailsService 추가



        return http.build();
    }
}




