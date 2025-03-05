package org.example.securityexam.securityExam4.repository;

import org.example.securityexam.securityExam4.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);


    boolean existsByUsername(String username);
}
