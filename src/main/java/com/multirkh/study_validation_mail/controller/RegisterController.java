package com.multirkh.study_validation_mail.controller;

import com.multirkh.study_validation_mail.dto.UserDto;
import com.multirkh.study_validation_mail.repository.UserRepository;
import com.multirkh.study_validation_mail.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
public class RegisterController {


    private final UserRepository memberRepository;
    private final UserService userService;


    @GetMapping("/register")
    public String showRegistrationForm(Model model) { //타임리프와 인터랙션을 위해서 필요한 변수다.
        model.addAttribute("user", new UserDto());
        return "signup_form";
    }

    @PostMapping("/register")
    public String processRegistration(UserDto userDto, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        userService.register(userDto, getSiteURL(request));
        return "register_success";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    private String getSiteURL(HttpServletRequest request) {
        //Logger log = LoggerFactory.getLogger(this.getClass().getName());
        String siteURL = request.getRequestURL().toString();
        String replacedUrl = siteURL.replace(request.getServletPath(), "");
        //log.info(":: siteURL = {}", siteURL);
        //log.info(":: siteURL = {}", siteURL);
        return replacedUrl;
    }
}
