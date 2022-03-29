package com.sparta.justboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@ServletComponentScan // @WebServlet 어노테이션이 동작하게 함
@SpringBootApplication
public class JustBoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(JustBoardApplication.class, args);
    }
}