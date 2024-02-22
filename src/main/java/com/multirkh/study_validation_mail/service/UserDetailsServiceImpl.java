package com.multirkh.study_validation_mail.service;

import com.multirkh.study_validation_mail.entity.User;
import com.multirkh.study_validation_mail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userEmail, password = null;
        String firstName, lastName, role = null;
        List<GrantedAuthority> authorityList = null;
        log.info("user.getId() = {}", username);
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User details not found for the user: " + username);
        } else {
            userEmail = user.getEmail();
            password = user.getPassword();
            authorityList = new ArrayList<>();
            authorityList.add(new SimpleGrantedAuthority(user.getRole()));
        }
        return new org.springframework.security.core.userdetails.User(userEmail, password, authorityList);
    }
}
