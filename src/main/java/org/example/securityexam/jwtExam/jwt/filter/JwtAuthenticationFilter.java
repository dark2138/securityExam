package org.example.securityexam.jwtExam.jwt.filter;

import ch.qos.logback.core.util.StringUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.securityexam.jwtExam.jwt.token.JwtAuthenticationToken;
import org.example.securityexam.jwtExam.jwt.utill.JwtTokenizer;
import org.example.securityexam.jwtExam.security.dto.CustomUserDetails;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
log.info("JWT Authentication Filter");

        // request로 부터 토큰을 얻어와야 함
        String token = getToken(request);
        // 시큐리티에게 token 넣어줌 == 시큐리티홀더에게 넘겨줌
        if (StringUtils.hasText(token)) {
            try {
                Authentication authentication = getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);



            } catch (Exception e) {
                log.error("JWT Fiter 1 - Internal Error : {}", e.getMessage());
                SecurityContextHolder.clearContext();
                throw new BadCredentialsException("JWT Fiter 2 - Internal Error");
            }
        }


        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(String token) {
        Claims claims = jwtTokenizer.parseAccessToken(token);
        String email = claims.getSubject();
        Long userId = claims.get("userId", Long.class);
        String name = claims.get("name", String.class);
        String username = claims.get("username", String.class);
        // 권한 리스트
        List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(claims);

        //UserDetails
        CustomUserDetails customUserDetails = new CustomUserDetails(username, "", name, grantedAuthorities);
        return new JwtAuthenticationToken(grantedAuthorities, customUserDetails, null);


    }

    private List<GrantedAuthority> getGrantedAuthorities(Claims claims) {
        List<String> roles = (List<String>) claims.get("roles");
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }


    private String getToken(HttpServletRequest request) {
        //헤더를 통해서 토큰을 넘겨줬다면...
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }

        //쿠키에 accessToken 이 있는지 찾아서 리턴
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }


}
