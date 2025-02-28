package org.example.securityexam.beforeSecurity;

import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
//@Component
//@WebFilter(urlPatterns = "/test/*")
public class CaramiFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("CaramiFilter init()");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("CaramiFilter doFilter() 실행전" + Thread.currentThread().getName());
        filterChain.doFilter(servletRequest, servletResponse);

        log.info("CaramiFilter doFilter() 실행후" + Thread.currentThread().getName());
    }


    @Override
    public void destroy() {
        log.info("CaramiFilter destroy()");
    }
}
