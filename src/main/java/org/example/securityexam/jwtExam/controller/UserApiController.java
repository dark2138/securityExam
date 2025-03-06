package org.example.securityexam.jwtExam.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.securityexam.jwtExam.domain.RefreshToken;
import org.example.securityexam.jwtExam.domain.Role;
import org.example.securityexam.jwtExam.domain.User;
import org.example.securityexam.jwtExam.dto.UserLoginResponseDTO;

import org.example.securityexam.jwtExam.jwt.utill.JwtTokenizer;
import org.example.securityexam.jwtExam.security.dto.UserLoginDTO;

import org.example.securityexam.jwtExam.service.RefreshTokenService;
import org.example.securityexam.jwtExam.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenService refreshTokenService;
    @GetMapping("/info")
    public String info(){
        return "info";
    }

    //eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb0BnbWFpbC5jb20iLCJ1c2VybmFtZSI6InExMjMiLCJuYW1lIjoicTEyMyIsInVzZXJJZCI6MTAsInJvbGVzIjpbIkFETUlOIiwiVVNFUiJdLCJpYXQiOjE3NDEyNDUyNDEsImV4cCI6MTc0MTI0NzA0MX0.yloGBnnBJdyfll55Owu6gcXJKSzjwuyIIoO993AGVMM

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDTO userLoginDto, HttpServletResponse response){
        //1. username이 우리 서버에 있는지..
        User user = userService.findByUsername(userLoginDto.getUsername());
        if(user == null){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 아이디 입니다.");
        }
        //2. 비밀번호비교
        if(!passwordEncoder.matches(userLoginDto.getPassword(),user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 올바르지 않습니다.");
        }
        //여기까지왔다면, 유저도 있고, 비밀번호도 맞고..   다음은??
        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        //토큰발급.
        String accessToken = jwtTokenizer.createAcessToken(user.getId(),
                user.getEmail(),
                user.getName(),
                user.getUsername(),
                roles);

        String refreshToken = jwtTokenizer.createRefreshToken(user.getId(),
                user.getEmail(),
                user.getName(),
                user.getUsername(),
                roles);

        //리프레시토큰을 데이터베이스에 저장
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setUserId(user.getId());

        refreshTokenService.addRefreshToken(refreshTokenEntity);


        //쿠키로도 저장하고 싶다면??
        Cookie accessTokenCookie = new Cookie("accessToken",accessToken);
        accessTokenCookie.setHttpOnly(true);  //보안   (쿠키값을 자바스크립트 같은곳에서 접근할수 없다.)
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(Math.toIntExact(JwtTokenizer.ACCESS_TOKEN_EXPIRES_COUNT/1000));   //쿠키 시간단위?? 초 단위..

        Cookie refreshTokenCookie = new Cookie("refreshToken",refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(Math.toIntExact(JwtTokenizer.REFRESH_TOKEN_EXPIRES_COUNT/1000));

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);


        //응답으로 보낼 값을 준비해줍니다.
        UserLoginResponseDTO loginResponseDto = UserLoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .name(user.getName())
                .build();

        return ResponseEntity.ok(loginResponseDto);
    }
}