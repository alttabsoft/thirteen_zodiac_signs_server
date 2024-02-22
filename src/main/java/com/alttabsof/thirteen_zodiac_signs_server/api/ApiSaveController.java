package com.alttabsof.thirteen_zodiac_signs_server.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ApiSaveController {

    @PostMapping("/upload")
    public String saveFile(@RequestParam MultipartFile multipartFile) {
        String a;
        if (!multipartFile.isEmpty()) {
            a = "multipartFile.getName() = " + multipartFile.getName();
            System.out.println("multipartFile.getName() = " + multipartFile.getName());
        }
        else{
            a = "I did not received any file";
            System.out.println("I did not received any file");
        }
        return a;
    }

    @GetMapping("/upload")
    public String saveFile() {
        return "dhdhdhdhdhhdhdhd";
    }
}
