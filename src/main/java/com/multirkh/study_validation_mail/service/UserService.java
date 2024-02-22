package com.multirkh.study_validation_mail.service;

import com.multirkh.study_validation_mail.dto.UserDto;
import com.multirkh.study_validation_mail.entity.Authority;
import com.multirkh.study_validation_mail.entity.User;
import com.multirkh.study_validation_mail.entity.UserAuthority;
import com.multirkh.study_validation_mail.repository.AuthorityRepository;
import com.multirkh.study_validation_mail.repository.UserAuthorityRepository;
import com.multirkh.study_validation_mail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    public int register(UserDto userDto){
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        String email = userDto.getEmail();
        User user = new User(email,encodedPassword);

        User savedUser = userRepository.save(user);
        List<Authority> authorityList = authorityRepository.findByName("USER");
        UserAuthority userAuthority = new UserAuthority(savedUser, authorityList.get(0));
        userAuthorityRepository.save(userAuthority);

        return user.getId();
    }
}
