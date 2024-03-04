package com.alttabsof.thirteen_zodiac_signs_server.api;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.alttabsof.thirteen_zodiac_signs_server.config.SecurityConstants.SAVE_DIRECTORY;

@RestController
public class ApiSaveController {

    @PostMapping("/upload")
    public String saveFile(@RequestParam(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        String a;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 사용자의 이름 가져오기
        String username = authentication.getName();
        System.out.println("username = " + username);
        if (!multipartFile.isEmpty()) {
            a = "multipartFile.getName() = " + multipartFile.getName();
            System.out.println("multipartFile.getName() = " + multipartFile.getName());
            String fullPath = SAVE_DIRECTORY + multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File(fullPath));
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
