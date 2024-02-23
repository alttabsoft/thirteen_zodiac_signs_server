package com.multirkh.study_validation_mail.config;

import com.multirkh.study_validation_mail.entity.Authority;
import com.multirkh.study_validation_mail.entity.User;
import com.multirkh.study_validation_mail.entity.UserAuthority;
import com.multirkh.study_validation_mail.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 로그인 인증에 필요한 수문장 역할을한다.<br/>이게 있으면
 * {@link org.springframework.security.core.userdetails.UserDetailsService}
 * 가 필요 없다.
 *
 * @author Kihyun
 */
@Configuration
@RequiredArgsConstructor
@Transactional
public class AuthenticationProviderImpl implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * @param authentication the authentication request object.
     * @return org.springframework.security.authentication.UsernamePasswordAuthenticationToken
     * @throws BadCredentialsException;
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<User> userList = userRepository.findByEmail(username);
        if (!userList.isEmpty()){
            User user = userList.get(0);
            if(!user.isVerified()){
                throw new BadCredentialsException("Not verified account");
            }
            if(passwordEncoder.matches(password, user.getPassword())){
                List<UserAuthority> userAuthorityList = userList.get(0).getUserAuthorityList();
                for(UserAuthority userAuthority : userAuthorityList){
                    System.out.println("userAuthority.getAuthority().getName() = " + userAuthority.getAuthority().getName());
                }
                List<SimpleGrantedAuthority> authoritySet = userAuthorityList
                        .stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getName()))
                        .toList();
                return new UsernamePasswordAuthenticationToken(username, password, authoritySet);
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
