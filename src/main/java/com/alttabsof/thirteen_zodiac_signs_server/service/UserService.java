package com.alttabsof.thirteen_zodiac_signs_server.service;

import com.alttabsof.thirteen_zodiac_signs_server.repository.UserAuthorityRepository;
import com.alttabsof.thirteen_zodiac_signs_server.RandomStringGenerator;
import com.alttabsof.thirteen_zodiac_signs_server.dto.UserDto;
import com.alttabsof.thirteen_zodiac_signs_server.entity.Authority;
import com.alttabsof.thirteen_zodiac_signs_server.entity.User;
import com.alttabsof.thirteen_zodiac_signs_server.entity.UserAuthority;
import com.alttabsof.thirteen_zodiac_signs_server.repository.AuthorityRepository;
import com.alttabsof.thirteen_zodiac_signs_server.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.alttabsof.thirteen_zodiac_signs_server.config.SecurityConstants.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private final JavaMailSender javaMailSender;

    public int register(UserDto userDto, String siteURL) {
        User user = preRegister(userDto);

        sendVerificationEmail(user.toUserDto(), siteURL); // 메일 보내는 기능 잠시 종료
        return user.getId();
    }

    public int testRegister(UserDto userDto, String siteURL) {
        User savedUser = preRegister(userDto);
        savedUser.setVerified();
        return savedUser.getId();
    }

    private User preRegister(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        String email = userDto.getEmail();
        User user = new User(email,encodedPassword, RandomStringGenerator.generateRandomString(64));
        User savedUser = userRepository.save(user); //userRepository 에 저장
        List<Authority> authorityList = authorityRepository.findByName("USER");
        UserAuthority userAuthority = new UserAuthority(savedUser, authorityList.get(0));
        userAuthorityRepository.save(userAuthority);
        return user;
    }

    private void sendVerificationEmail(UserDto userDto, String siteURL) {
        Logger log = LoggerFactory.getLogger(this.getClass().getName());
        try {
            String toAddress = userDto.getEmail();
            String fromAddress = MAIL_USER_NAME;
            String senderName = COMPANY_NAME;
            String subject = "Please verify your registration";
            String content = "Dear [[name]],<br>" +
                    "Please click the link below to verify your registration:<br>" +
                    "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" +
                    "Thank you,<br> " +
                    "Maybe you want to know verificationCode is" +
                    "[[verificationCode]]" +
                    "Your company name.";

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            mimeMessageHelper.setFrom(fromAddress);
            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setSubject(subject);

            content = content.replace("[[name]]", userDto.getFullName());
            String verifyURL = FRONT_WEB_URL + "/verify?code=" + userDto.getVerificationCode();
            content = content.replace("[[URL]]", verifyURL);
            //content = content.replace("[[verificationCode]]", userDto.getVerificationCode());

            mimeMessageHelper.setText(content, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("error has occurred = {}", e.toString());
        }
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);
        if (user == null || user.isVerified()) {
            return false;
        } else {
            user.setVerified();
            userRepository.save(user);
            return true;
        }
    }
}

