package com.multirkh.study_validation_mail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {
    public static String MAIL_USER_NAME;
    public static String COMPANY_NAME;
    public static String JWT_KEY;
    public static String JWT_HEADER;

    @Value("${MAIL_USERNAME}")
    private void setMailUserName(String value) {
        MAIL_USER_NAME = value;
    }

    @Value("${COMPANY_NAME}")
    private void setCompanyName(String value){
        COMPANY_NAME = value;
    }

    @Value("${JWT_KEY}")
    private void setJwtKey(String value){
        JWT_KEY = value;
    }

    @Value("${JWT_HEADER}")
    private void setJwtHeader(String value){
        JWT_HEADER = value;
    }


}
