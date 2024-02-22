package com.multirkh.study_validation_mail.api;

import com.multirkh.study_validation_mail.dto.UserDto;
import com.multirkh.study_validation_mail.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiRegisterController {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final UserService userService;

    @PostMapping("/api/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto, HttpServletRequest request) {
        ResponseEntity<String> response = null;
        try {
            int registeredUserId = userService.register(userDto, getSiteURL(request));
            if (registeredUserId > 0) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
