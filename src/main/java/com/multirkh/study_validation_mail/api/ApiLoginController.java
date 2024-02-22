package com.multirkh.study_validation_mail.api;

import com.multirkh.study_validation_mail.dto.UserDto;
import com.multirkh.study_validation_mail.entity.User;
import com.multirkh.study_validation_mail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiLoginController {
    private final UserRepository userRepository;

    @RequestMapping("/user")
    public UserDto getUserDetailsAfterLogin(Authentication authentication){ //authentication은 AuthenticationProviderImpl 에 있습니다~
        List<User> userList = userRepository.findByEmail(authentication.getName());
        if(!userList.isEmpty()){
            return userList.get(0).toUserInfoDTO();
        } else {
            return null;
        }
    }
}
