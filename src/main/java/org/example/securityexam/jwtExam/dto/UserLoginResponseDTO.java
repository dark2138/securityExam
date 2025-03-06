package org.example.securityexam.jwtExam.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder //  중간에서 값을 변경하지 못하게 Setter 쓰지 않음
public class UserLoginResponseDTO {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String name;

}
