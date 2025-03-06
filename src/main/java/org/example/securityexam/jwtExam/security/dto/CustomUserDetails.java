package org.example.securityexam.jwtExam.security.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final String name;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, String name, List<GrantedAuthority> roles) {
        this.username = username;
        this.password = password;
        this.name = name;
       // this.authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        this.authorities = roles;
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 사용자 계정이 만료되었나? (정확히 구현해야겠지만 펀의상 true

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }


    public String getName() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
