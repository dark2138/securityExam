package org.example.securityexam.securityExam4.security;

import lombok.RequiredArgsConstructor;
import org.example.securityexam.securityExam4.domain.Role;
import org.example.securityexam.securityExam4.domain.User;
import org.example.securityexam.securityExam4.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailservice implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 주어진 사용자명으로 데이터베이스에서 사용자 정보를 조회
        User user = userRepository.findByUsername(username);

        // 사용자가 존재하지 않으면 예외를 던짐
        if (user == null) {
            throw new UsernameNotFoundException(username + " 해당하는 사용자가 없습니다");
        }

        // Spring Security의 User 클래스를 사용하여 UserBuilder 객체 생성
        UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);

        // 사용자의 비밀번호 설정
        userBuilder.password(user.getPassword());

        // 사용자의 역할(권한) 설정
        // 1. user.getRoles()로 사용자의 역할 목록을 가져옴
        // 2. stream()을 사용하여 스트림으로 변환
        // 3. map(Role::getName)으로 각 Role 객체에서 이름만 추출
        // 4. toList()로 다시 리스트로 변환
        // 5. toArray(new String[0])로 문자열 배열로 최종 변환
        userBuilder.roles(user.getRoles().stream().map(Role::getName).toList().toArray(new String[0]));

        // 설정이 완료된 UserBuilder로부터 UserDetails 객체를 생성하여 반환
        return userBuilder.build();
    }
    /*
사용자 정보 찾기:

이 서비스는 마치 도서관 사서와 같아요. 누군가 로그인하려고 할 때, 이 서비스가 그 사람의 정보를 찾아줍니다.

맞춤형 인증:

여러분의 애플리케이션에 맞는 특별한 방식으로 사용자를 확인할 수 있어요. 예를 들어, 데이터베이스나 다른 저장소에서 사용자 정보를 가져올 수 있습니다.


     */
    // CustomUserDetailService는 여러분의 애플리케이션에서 사용자를 안전하고
    // 효과적으로 인증하는 데 도움을 주는 특별한 도구입니다. 이를 통해 누가 로그인할 수 있고,
    // 어떤 권한을 가질지를 여러분이 직접 정할 수 있어요.

    /*

    UserBuilder
사용자 객체 생성 도구:
UserBuilder는 UserDetails 인터페이스를 구현하는 사용자 객체를 쉽고 명확하게 생성하는 데 사용됩니다1.

유연한 설정:
사용자의 이름, 비밀번호, 권한 등을 단계별로 설정할 수 있어 유연성이 높습니다1.

객체 생성 패턴:
빌더 패턴을 사용하여 가독성 좋은 코드로 사용자 객체를 생성할 수 있습니다1.

UserDetailsService
사용자 정보 로드:
데이터베이스나 다른 저장소에서 사용자 정보를 로드하는 역할을 합니다12.

인터페이스:
loadUserByUsername 메소드를 정의하는 인터페이스로, 이 메소드는 사용자명을 받아 해당 사용자의 정보를 UserDetails 객체로 반환합니다25.

인증 과정에서의 역할:
AuthenticationProvider가 인증을 수행할 때 UserDetailsService를 사용하여 사용자 정보를 가져옵니다57.

주요 차이점
목적:

UserBuilder: 사용자 객체 생성

UserDetailsService: 저장소에서 사용자 정보 검색

사용 시점:

UserBuilder: 주로 테스트나 초기 설정 시 사용

UserDetailsService: 실제 인증 과정에서 사용

구현 방식:

UserBuilder: 주로 메서드 체이닝을 통해 객체 생성

UserDetailsService: 인터페이스를 구현하여 커스텀 로직 정의

반환 타입:

UserBuilder: UserDetails 객체 생성

UserDetailsService: UserDetails 객체 반환

UserBuilder는 사용자 객체를 생성하는 도구이고, UserDetailsService는 실제 인증 과정에서 사용자 정보를
로드하는 서비스입니다. 둘 다 Spring Security의 인증 메커니즘에서 중요한 역할을 하지만, 각각의 책임과 사용
 목적이 다릅니다.









     */
}
