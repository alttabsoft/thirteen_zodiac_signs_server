package com.multirkh.study_validation_mail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {
    public static String MailUserName;
    public static String CompanyName;
    @Value("${MAIL_USERNAME}")
    private void setMailUserName(String value) {
        MailUserName = value;
    }

    @Value("${COMPANY_NAME}")
    private void setCompanyName(String value){
        CompanyName = value;
    }
}
