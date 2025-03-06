package org.example.securityexam.jwtExam.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lion_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "registration_date", nullable = false, updatable = false) // updatable 수정가능여부
    private LocalDateTime registrationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // 연결 테이블 이름
            joinColumns = @JoinColumn(name = "user_id"), // 현재 엔티티(LionUser)의 외래 키
            inverseJoinColumns = @JoinColumn(name = "role_id") // 반대 엔티티(Role)의 외래 키
    )
    private Set<Role> roles;

    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDateTime.now();
    }
}
