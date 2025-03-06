package org.example.securityexam.jwtExam.jwt.utill;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component // Spring의 Bean으로 등록하여 IoC 컨테이너에서 관리되도록 설정
public class JwtTokenizer {

    // ACCESS 및 REFRESH 토큰 생성에 사용할 비밀 키를 저장하는 필드
    private final byte[] accessSecret; // ACCESS 토큰 서명에 사용할 비밀 키
    private final byte[] refreshSecret; // REFRESH 토큰 서명에 사용할 비밀 키

    // ACCESS 및 REFRESH 토큰의 만료 시간을 상수로 정의
    public static final Long ACCESS_TOKEN_EXPIRES_COUNT = 30 * 60 * 1000L; // ACCESS 토큰 만료 시간: 30분 (밀리초 단위)
    public static final Long REFRESH_TOKEN_EXPIRES_COUNT = 7 * 24 * 60 * 60 * 1000L; // REFRESH 토큰 만료 시간: 7일 (밀리초 단위)

    // 생성자: Spring의 @Value 어노테이션을 사용해 application.properties 또는 application.yml 파일에서 비밀 키 값을 주입받음
    public JwtTokenizer(@Value("${jwt.secretKey}") String accessSecret,
                        @Value("${jwt.refreshKey}") String refreshSecret) {
        this.accessSecret = accessSecret.getBytes(StandardCharsets.UTF_8); // ACCESS 비밀 키를 UTF-8 바이트 배열로 변환
        this.refreshSecret = refreshSecret.getBytes(StandardCharsets.UTF_8); // REFRESH 비밀 키를 UTF-8 바이트 배열로 변환
    }

    /**
     * JWT 토큰 생성 메서드
     *
     * @param id        사용자 ID
     * @param email     사용자 이메일 (고유 식별자)
     * @param name      사용자 이름
     * @param username  사용자 닉네임 또는 사용자명
     * @param roles     사용자의 권한 목록
     * @param expire    토큰 만료 시간 (밀리초 단위)
     * @param secretKey 서명에 사용할 비밀 키 (ACCESS 또는 REFRESH용)
     * @return 생성된 JWT 문자열
     */
    private String createToken(Long id, String email, String name, String username, List<String> roles,
                               Long expire, byte[] secretKey) {
        // JWT Claims 설정: JWT에 포함될 정보를 정의
        Claims claims = Jwts.claims().setSubject(email); // 'sub' 클레임: 고유 식별자로 이메일 사용
        claims.put("username", username); // 'username' 클레임 추가
        claims.put("name", name);         // 'name' 클레임 추가
        claims.put("userId", id);         // 'userId' 클레임 추가
        claims.put("roles", roles);       // 'roles' 클레임 추가 (사용자 권한)

        return Jwts.builder()
                .setClaims(claims) // 위에서 설정한 클레임 추가
                .setIssuedAt(new Date()) // 'iat' 클레임: 토큰 발급 시간 설정
                .setExpiration(new Date(new Date().getTime() + expire)) // 'exp' 클레임: 만료 시간 설정 (현재 시간 + expire)
                .signWith(getSigningKey(secretKey)) // HMAC-SHA 알고리즘을 사용해 서명 추가 (비밀 키 사용)
                .compact(); // JWT를 문자열로 직렬화하여 반환
    }

    /**
     * HMAC-SHA 알고리즘을 위한 서명 키 생성 메서드
     *
     * @param secretKey 비밀 키 (바이트 배열)
     * @return Key 객체 (HMAC-SHA 알고리즘용)
     */
    private static Key getSigningKey(byte[] secretKey) {
        return Keys.hmacShaKeyFor(secretKey); // io.jsonwebtoken.security.Keys 클래스 사용해 HMAC-SHA Key 생성
    }

    /**
     * ACCESS Token 생성 메서드
     *
     * @param id       사용자 ID
     * @param email    사용자 이메일
     * @param name     사용자 이름
     * @param username 사용자 닉네임 또는 사용자명
     * @param roles    사용자의 권한 목록
     * @return 생성된 ACCESS Token 문자열
     */
    public String createAcessToken(Long id, String email, String name, String username, List<String> roles) {
        return createToken(id, email, name, username, roles, ACCESS_TOKEN_EXPIRES_COUNT, accessSecret);
    }

    /**
     * REFRESH Token 생성 메서드
     *
     * @param id       사용자 ID
     * @param email    사용자 이메일
     * @param name     사용자 이름
     * @param username 사용자 닉네임 또는 사용자명
     * @param roles    사용자의 권한 목록
     * @return 생성된 REFRESH Token 문자열
     */
    public String createRefreshToken(Long id, String email, String name, String username, List<String> roles) {
        return createToken(id, email, name, username, roles, REFRESH_TOKEN_EXPIRES_COUNT, refreshSecret);
    }


    public Claims parseToken(String token, byte[] secretKey){
        return  Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserIdFromToken(String token){
        //Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwidXNlcm5hbWUiOiJ0ZXN0dXNlciIsIm5hbWUiOiJ0ZXN0IiwidXNlcklkIjoxLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNzQxMjIyMjIzLCJleHAiOjE3NDEyMjQwMjN9.Mw2TmisHqjWyECxjRbSYMvja2L41r1-_7m4IllBLsS4
        if(token == null || token.isBlank()){
            throw new IllegalArgumentException("JWT 토큰이 없습니다.");
        }

        if(!token.startsWith("Bearer ")){
            throw new IllegalArgumentException("유효하지 않은 형식입니다.");
        }
        Claims claims = parseToken(token, accessSecret);
        if(claims == null){
            throw new IllegalArgumentException("유효하지 않은 형식입니다.");
        }
        Object userId = claims.get("userId");
        if(userId instanceof Number){
            return ((Number)userId).longValue();
        }else{
            throw new IllegalArgumentException("JWT토큰에서 userId를 찾을 수 없습니다.");
        }

    }

    public Claims parseAccessToken(String accessToken){
        return parseToken(accessToken,accessSecret);
    }

    public Claims parseRefreshToken(String refreshToken){
        return parseToken(refreshToken,refreshSecret);
    }

}