package com.heath.bo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HeathBoApplication {
    public static void main(String[] args) {
        SpringApplication.run(HeathBoApplication.class, args);
    }
}
