package com.multirkh.study_validation_mail.config;

import com.multirkh.study_validation_mail.entity.User;
import com.multirkh.study_validation_mail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthenticationProviderImpl implements AuthenticationProvider { // 로그인 인증에 필요한 수문장 역할 이게 있으면 UserDetailsService 가 필요 없다.
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException { //인증 성공시 토큰 반환
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<User> userList = userRepository.findByEmail(username);
        if (!userList.isEmpty()){
            if(passwordEncoder.matches(password, userList.get(0).getPassword())){
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(userList.get(0).getRole()));
                return new UsernamePasswordAuthenticationToken(username, password, authorities);
            } else {
                throw new BadCredentialsException("Invalid password");
            }
        } else {
            throw new BadCredentialsException("No user registered with this details!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
