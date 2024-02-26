package com.multirkh.study_validation_mail.service;

import com.multirkh.study_validation_mail.RandomStringGenerator;
import com.multirkh.study_validation_mail.dto.UserDto;
import com.multirkh.study_validation_mail.entity.Authority;
import com.multirkh.study_validation_mail.entity.User;
import com.multirkh.study_validation_mail.entity.UserAuthority;
import com.multirkh.study_validation_mail.repository.AuthorityRepository;
import com.multirkh.study_validation_mail.repository.UserAuthorityRepository;
import com.multirkh.study_validation_mail.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.multirkh.study_validation_mail.config.SecurityConstants.COMPANY_NAME;
import static com.multirkh.study_validation_mail.config.SecurityConstants.MAIL_USER_NAME;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private final JavaMailSender javaMailSender;

    public int register(UserDto userDto, String siteURL) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        String email = userDto.getEmail();
        User user = new User(email,encodedPassword, RandomStringGenerator.generateRandomString(64));
        User savedUser = userRepository.save(user); //userRepository 에 저장
        List<Authority> authorityList = authorityRepository.findByName("USER");
        UserAuthority userAuthority = new UserAuthority(savedUser, authorityList.get(0));
        userAuthorityRepository.save(userAuthority);

        //sendVerificationEmail(user.toUserDto(), siteURL); // 메일 보내는 기능 잠시 종료
        return user.getId();
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
            String verifyURL = siteURL + "/verify?code=" + userDto.getVerificationCode();
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
