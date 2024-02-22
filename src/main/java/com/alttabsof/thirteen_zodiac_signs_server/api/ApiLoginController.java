package com.alttabsof.thirteen_zodiac_signs_server.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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