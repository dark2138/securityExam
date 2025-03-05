package org.example.securityexam.securityExam4.repository;

import org.example.securityexam.securityExam4.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);



}
