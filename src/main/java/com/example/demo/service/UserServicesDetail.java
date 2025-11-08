package com.example.demo.service;

import org.springframework.data.domain.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UserServicesDetail implements UserDetailsService{
    // @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServicesDetail(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
     
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username);
        return new UserServices(user);   
    }
    
    public UserModel getUseryId(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
    public UserModel createTeacher(String fullUsername, String rawPassword, String email,MultipartFile avatarFile) throws Exception {
        System.out.println("Creating a new teacher: " + fullUsername + ", " + email);
        ensureUnique(fullUsername, email);

        UserModel u = new UserModel();
        u.setUsername(fullUsername.trim().toLowerCase());
        u.setEmail(email.trim().toLowerCase());
        u.setPassword(passwordEncoder.encode(rawPassword));
        u.setRole("ROLE_TEACHER");
        u.setEnabled(true);
        userProfileService.create(u, avatarFile);
        return userRepository.save(u);
    }
    public UserModel createAdmin(String fullUsername, String rawPassword, String email) {
        ensureUnique(fullUsername, email);

        UserModel u = new UserModel();
        u.setUsername(fullUsername.trim().toLowerCase());
        u.setEmail(email.trim().toLowerCase());
        u.setPassword(passwordEncoder.encode(rawPassword));
        u.setRole("ROLE_ADMIN");
        u.setEnabled(true);
        return userRepository.save(u);
    }

    private void ensureUnique(String username, String email) {
        if (userRepository.existsByEmail(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByUsername(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
    }
     public Page<UserModel> getUsersPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }
    
}