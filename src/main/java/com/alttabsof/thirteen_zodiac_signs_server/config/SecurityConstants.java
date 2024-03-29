package com.alttabsof.thirteen_zodiac_signs_server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {
    public static String MAIL_USER_NAME;
    public static String COMPANY_NAME;
    public static String JWT_PRIVATE_KEY;
    public static String JWT_HEADER;
    public static String SAVE_DIRECTORY;
    public static String FRONT_WEB_URL;

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
        JWT_PRIVATE_KEY = value;
    }

    @Value("${JWT_HEADER}")
    private void setJwtHeader(String value){
        JWT_HEADER = value;
    }

    @Value("${SAVE_DIRECTORY}")
    private void setSaveDirectoryY(String value){
        SAVE_DIRECTORY = value;
    }

    @Value("${FRONT_WEB_URL}")
    private void setFrontWebUrl(String value) {
        FRONT_WEB_URL = value;
    }

}
