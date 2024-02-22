package com.alttabsof.thirteen_zodiac_signs_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ThirteenZodiacSignsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ThirteenZodiacSignsApplication.class, args);
    }
}
