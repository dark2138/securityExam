package org.example.securityexam.securityExam;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //1. 기본 설정
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().authenticated() //모든 요청에 대해서 인증을 요구하겠다.
//                )
//                .formLogin(Customizer.withDefaults());


//      2. 추가로. 원하는 페이지는 인증없이 접근 가능.   폼 로그인 인증 방식을 사용자가 원하는 설정으로..
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/hello","/loginForm", "/fail", "/test/*").permitAll() //인증없이 접근 가능
                       // .requestMatchers("/info", "/success").authenticated() // 인증된 사용자만 접근 가능

                        .anyRequest().authenticated() //모든 요청에 대해서 인증을 요구하겠다.
                )
                .formLogin(formLoin -> formLoin
                        .loginProcessingUrl("/login_Proc")  // 기본 login form
                        //.loginPage("/login")  //원하는 로그인 페이지 설정
                        //.defaultSuccessUrl("/success") //인증에 성공하면 가고싶은 페이지 설정
                        //.failureUrl("/fail")//실패했다면??
                        .usernameParameter("userId") //  /login form의 parameter
                        .passwordParameter("password")//  /login form의 parameter
                        .successHandler((request, response, authentication) ->{
                            log.info("로그인 성공!! 사용자: {}", authentication.getName());
                            response.sendRedirect("/info");
                        }) // 로그인 성공시 , 실패시 다른일 (ex> 로그를 남김, redirect로 넘김)
                        .failureHandler((request, response, exception) -> {
                            log.info("로그인 실패!! 사용자: {}", exception.getMessage());
                            response.sendRedirect("/login");

                        })



                );

        // 3. 로그아웃
        http
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 요청을 받을 URL (기본값: "/logout")
                        .logoutSuccessUrl("/login") // 로그아웃 성공 후 이동할 URL
                        .invalidateHttpSession(true) // 세션 무효화 (기본값: true)  로그아웃 시 자동으로 세션 무효화
                        // Spring Security 로그아웃 프로세스에서 자동 적용
                        // 별도의 코드 없이 로그아웃할 때 세션을 삭제할 수 있음
                        .deleteCookies("JSESSIONID") // 로그아웃 시 쿠키 삭제 (선택 사항)
                        .addLogoutHandler((request, response, authentication) -> {
                            log.info("사용자 {} 로그아웃", authentication != null ? authentication.getName() : "알 수 없음");
/*
                            HttpSession session = request.getSession();
                            if(session != null) {
                                session.invalidate();
                            }

 */
                            /*
                            직접 HttpSession을 가져와 무효화하는 방식.

                            특정한 시점에서 세션을 바로 삭제하고 싶을 때 사용.
                            로그아웃뿐만 아니라, 세션을 강제로 만료시키고 싶을 때 활용 가능.

                            invalidateHttpSession , HttpSession을 둘 중 하나만 하면 됨
                             */

                        }) // 로그아웃 이벤트 처리
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/login"); // 커스텀 로그아웃 성공 처리
                        })
                );

        /*
        addLogoutHandler()	로그아웃 요청이 처리되는 동안	세션 제거, 사용자 데이터 정리, 추가적인 로그아웃 로직 실행
        logoutSuccessHandler()	로그아웃이 완료된 후	사용자에게 응답(리디렉트, JSON 응답 등)

        실제 동작 흐름
        사용자가 /logout 요청을 보냄
        addLogoutHandler() 실행됨 (사용자 세션 무효화, 로그 기록 등 추가 작업)
        Spring Security가 내부적으로 세션 삭제, 쿠키 제거 처리
        로그아웃이 완료됨
        logoutSuccessHandler() 실행됨 (리디렉트, 응답 처리)
         */

        return http.build();
    }
}
