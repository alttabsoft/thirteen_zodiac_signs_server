package com.multirkh.study_validation_mail.controller;

import com.multirkh.study_validation_mail.DTO.UserRegisterDTO;
import com.multirkh.study_validation_mail.repository.UserRepository;
import com.multirkh.study_validation_mail.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final UserRepository memberRepository;
    private final UserService userService;


    @GetMapping("/register")
    public String showRegistrationForm(Model model) { //타임리프와 인터랙션을 위해서 필요한 변수다.
        model.addAttribute("member", new UserRegisterDTO());
        return "signup_form";
    }

    @PostMapping("/register")
    public String processRegistration(UserRegisterDTO user, HttpServletRequest request){
        userService.register(getSiteURL(request),user);
        return "register_success";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        String replacedUrl = siteURL.replace(request.getServletPath(), "");
        log.info(":: siteURL = {}", siteURL);
        log.info(":: siteURL = {}", siteURL);
        return replacedUrl;
    }
}
