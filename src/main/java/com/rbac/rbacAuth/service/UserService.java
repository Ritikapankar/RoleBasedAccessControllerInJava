package com.rbac.rbacAuth.service;

import com.rbac.rbacAuth.entity.Role;
import com.rbac.rbacAuth.entity.User;
import com.rbac.rbacAuth.jwt.JwtUtil;
import com.rbac.rbacAuth.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository,
                       JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String register(User user) {

        user.setPassword(
                encoder.encode(user.getPassword())
        );

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        userRepository.save(user);

        return "User Registered";
    }

    public String login(User user) {

        User dbUser = userRepository
                .findByUsername(user.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        if (!encoder.matches(
                user.getPassword(),
                dbUser.getPassword())) {

            throw new RuntimeException("Invalid Password");
        }

        return jwtUtil.generateToken(dbUser);
    }
}