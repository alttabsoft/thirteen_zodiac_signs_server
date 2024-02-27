package com.multirkh.study_validation_mail.api;

import com.multirkh.study_validation_mail.dto.UserDto;
import com.multirkh.study_validation_mail.entity.User;
import com.multirkh.study_validation_mail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiLoginController {
    @GetMapping("/account")
    public String getAccounDetails(){
        return "Here are the account details from the DB";
    }

    @GetMapping("/user")
    public String getUser(){
        return "gooood";
    }
    @PostMapping("/account")
    public String postAccountDetails(){
        return "Your Posting Well Received";
    }
}
