package org.example.securityexam.securityExam3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/hello")
@RequiredArgsConstructor
public class HelloController {

    private final HelloService helloService;

    @GetMapping("/info")
    public String info() {

        String msg = null;
        // SecurityContext에서 현재 인증 정보를 가져옴
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없거나 인증되지 않은 경우 처리
        // 주의: 이 조건문의 로직에 오류가 있습니다. '||' 대신 '&&'를 사용해야 합니다.
        if (auth == null || !auth.isAuthenticated()) {
            msg = "로그인 사용자가 없습니다";
            return msg; // 이 부분이 누락되어 있어 로직 오류가 발생할 수 있습니다.
        }

        // 인증된 사용자의 주체(Principal) 정보를 가져옴
        Object principal = auth.getPrincipal();

        // Principal이 UserDetails 인터페이스를 구현한 경우
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            // UserDetails에서 사용자 이름을 추출하여 메시지 생성
            msg = "현재 로그인 한 사용자 : " + userDetails.getUsername();
        } else {
            // UserDetails가 아닌 경우, Principal 객체의 toString() 메서드를 호출하여 메시지 생성
            msg = "현재 로그인 한 사용자 : " + principal.toString();
        }

        // 생성된 메시지 반환
        return msg;
    }
    /*

    사용자가 요청을 보내면, 서블릿 컨테이너가 이를 받아 처리합니다.

요청은 Spring Security 필터 체인을 통과하며, 이 과정에서 인증 정보가 SecurityContext에 저장됩니다.

컨트롤러 메서드에서는 SecurityContextHolder를 통해 현재 스레드의 SecurityContext에 접근하고, 여기서 Authentication 객체를 가져옵니다.

Authentication 객체에서 Principal(주체) 정보를 추출하여 사용자 정보를 확인합니다.


   쓰레드 로컬(ThreadLocal)은 Spring Security에서 중요한 역할을 수행합니다:

SecurityContext 저장: Spring Security는 기본적으로 ThreadLocal을 사용하여 SecurityContext를 저장합니다2. 이를 통해 각 요청(스레드)마다 독립적인 인증 정보를 유지할 수 있습니다.

스레드별 컨텍스트 분리: 각 HTTP 요청은 별도의 스레드에서 처리되며, ThreadLocal을 사용하여 각 스레드가 독립적인 SecurityContext를 가질 수 있게 합니다2.

접근성 향상: 요청 처리 중 여러 클래스에서 SecurityContext에 쉽게 접근할 수 있게 합니다2.

메모리 관리: 요청 처리가 끝난 후 스레드가 스레드 풀로 반환될 때, ThreadLocal을 사용하여 보안 컨텍스트를 쉽게 제거할 수 있습니다2.

동기화 문제 해결: 멀티스레드 환경에서 각 스레드가 독립적인 변수를 가질 수 있게 하여, 동기화 없이도 안전하게 데이터를 관리할 수 있습니다13.

SecurityContextHolder는 SecurityContext를 관리하고 접근하는 방법을 제공하는 유틸리티 클래스인 반면, SecurityContext는 실제 인증 정보(Authentication)를 담고 있는 컨테이너 역할을 합니다.
SecurityContextHolder를 통해 애플리케이션의 어느 부분에서든 현재 인증된 사용자의 정보에 안전하게 접근할 수 있습니다.

     SecurityContextHolder는 한마디로 창고지기이다.

     */


    @GetMapping
    public String aaa() {
        return "그릭요거트";
    }

    @GetMapping("/bbb")
    public String bbb() {
        // HelloService의 userLog() 를 호출하면, 현재 로그인한 사용자 이름을 log.info 로 출력

        helloService.userLog();

        return "bbb";
    }
    // 간단하게
    @GetMapping("/ccc")
    public String ccc(@AuthenticationPrincipal UserDetails userDetails) {



        return "ccc " + userDetails.getUsername();
    }
}
