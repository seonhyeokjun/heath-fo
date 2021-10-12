package com.seon.springvueproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringVueProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringVueProjectApplication.class, args);
    }
}
