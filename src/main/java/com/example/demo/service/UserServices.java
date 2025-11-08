package com.example.demo.service;

import java.util.Collection;
import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.UserModel;



public class UserServices implements UserDetails {
    private UserModel user;

    public UserServices(UserModel user) {
        this.user = user;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("User role: " + user.getRole());
        // String role = user.getRole();
        // return List.of(new SimpleGrantedAuthority(role != null ? "ROLE_"+role : "ROLE_TEACHER"));
        return List.of(new SimpleGrantedAuthority("ROLE_"+ user.getRole()));
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

}