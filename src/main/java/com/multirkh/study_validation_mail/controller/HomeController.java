package com.multirkh.study_validation_mail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {
    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }
}
