package org.example.securityexam.jwtExam.repository;



import org.example.securityexam.jwtExam.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
