package org.example.securityexam.beforeSecurity;

import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@Order(1)
public class UserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("UserFilter iniy() ");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            log.info("UserFilter doFilter() 실행전");

            // 스레드 로컬에 저장하고 싶은 객체가 존재한다면???
            // 복잡한 로직들이 실행되서 값을 가져오는 경우
            // UserContext.setUser(new User("carami"));

            User user = extractUser(servletRequest);
            UserContext.setUser(user);


            filterChain.doFilter(servletRequest, servletResponse);

            log.info("UserFilter doFilter() 실행후");
        } finally {
            UserContext.clearUser();
        }
    }

    private User extractUser(ServletRequest request) {
        // 복잡한 로직을 통해서 사용자의 정보를 추출한다면?
        String name = request.getParameter("name");
        return new User(name);

    }


    @Override
    public void destroy() {
        log.info("UserFilter destroy()");
    }


}
