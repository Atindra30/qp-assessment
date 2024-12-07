package com.qp.grocery.Service;

import com.qp.grocery.Model.Users;
import com.qp.grocery.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getName())
                        .password(user.getPassword())
                        .roles(String.valueOf(user.getRole()))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
