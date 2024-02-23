package com.multirkh.study_validation_mail.config;

import com.multirkh.study_validation_mail.RandomStringGenerator;
import com.multirkh.study_validation_mail.entity.Authority;
import com.multirkh.study_validation_mail.entity.User;
import com.multirkh.study_validation_mail.entity.UserAuthority;
import com.multirkh.study_validation_mail.repository.AuthorityRepository;
import com.multirkh.study_validation_mail.repository.UserAuthorityRepository;
import com.multirkh.study_validation_mail.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthenticationProviderImplTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    UserAuthorityRepository userAuthorityRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    public void beforeSetting(){
        User user = new User("helloWorld@example.com", passwordEncoder.encode("helloWorld"), RandomStringGenerator.generateRandomString(64));
        Authority authorityUser = authorityRepository.save(new Authority("USER"));
        Authority authorityAdmin = authorityRepository.save(new Authority("ADMIN"));
        userRepository.save(user);
        userAuthorityRepository.save(new UserAuthority(user, authorityUser));
        userAuthorityRepository.save(new UserAuthority(user, authorityAdmin));
        user.setVerified();
        em.flush();
        em.clear();
    }


    @Test
    public void authenticateTest() throws AuthenticationException {
        // given
        //User foundUser = userRepository.findByEmail("helloWorld@example.com").get(0);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken("helloWorld@example.com", "helloWorld"));
        Authentication authentication = context.getAuthentication();


        // when
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        List<User> userList = userRepository.findByEmail(username);
        if (!userList.isEmpty()) {
            User user = userList.get(0);
            if (!user.isVerified()) {
                throw new BadCredentialsException("Not verified account");
            }
            if (passwordEncoder.matches(password, user.getPassword())) {
                List<UserAuthority> userAuthorityList = userList.get(0).getUserAuthorityList();
                for (UserAuthority userAuthority : userAuthorityList) {
                    System.out.println("userAuthority = " + userAuthority.getAuthority().getName());
                }
                List<SimpleGrantedAuthority> authoritySet = userAuthorityList
                        .stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getName()))
                        .toList();

                SimpleGrantedAuthority simpleGrantedAuthority = authoritySet.get(0);
                Assertions.assertThat(authoritySet.get(0).getAuthority()).isEqualTo("USER");
            }
        }
    }
}