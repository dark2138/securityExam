package org.example.securityexam.jwtExam.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.example.securityexam.jwtExam.domain.Role;
import org.example.securityexam.jwtExam.domain.User;
import org.example.securityexam.jwtExam.repository.RoleRepository;
import org.example.securityexam.jwtExam.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User findByUsername(String username){

        System.out.println("1dddd");
        System.out.println(userRepository.findByUsername(username));

        return userRepository.findByUsername(username);
    }

    // 회원가입
    /*
    @Transactional
    public User register(User user) {
        // 롤 정보를 User 엔티티에 채워줄 필요가 있다
        // 일단 회원가입 요청이 들어오면 USER 권한으로 가입

        Role userRole =  roleRepository.findByName("USER").get();
        user.setRoles(Collections.singleton(userRole));
        // 패스워드는 반드시 암호화(인코딩) 해서 넣어줌
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
    */

/*

Collections.singleton(userRole)을 사용한 이유는 다음과 같습니다:

단일 역할 부여:

회원가입 시 기본적으로 "USER" 역할 하나만 부여하려는 의도입니다.

singleton() 메소드는 단 하나의 요소만을 포함하는 불변 Set을 생성합니다.

효율성:

단일 요소 Set이 필요할 때, new HashSet<>(Arrays.asList(userRole))과 같은 방식보다 더 효율적입니다.

메모리 사용이 적고, 생성 속도가 빠릅니다.

불변성 보장:

singleton()으로 생성된 Set은 수정할 수 없습니다. 이는 역할 정보의 무결성을 보장합니다.

간결성:

코드가 간결해지며, 의도를 명확히 표현합니다.

User 엔티티의 roles 필드 타입:

User 엔티티의 roles 필드가 Set<Role> 타입일 것으로 추정됩니다.

setRoles() 메소드는 Set 타입의 인자를 받을 것입니다.

 */

    //username에 해당하는 사용자가 있는지 체크.
    public boolean existsUser(String username){
        return userRepository.existsByUsername(username);
    }
    // 회원가입
    /*
    @Transactional
    public User registerUser(UserRegisterDTO registerDTO) {

        String encrodedPassword = passwordEncoder.encode(registerDTO.getPassword());

      Set<Role> roleSet =   registerDTO.getRoles().stream()
                .map(roleRepository::findByName)
                .flatMap(Optional::stream) // Optional이 비워있다면, 무시하고 값만 추출
                .collect(Collectors.toSet());


        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(encrodedPassword);
        user.setName(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setRoles(roleSet);


        return userRepository.save(user);
    }
*/

}
