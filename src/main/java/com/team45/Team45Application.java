package com.team45;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

@SpringBootApplication
public class Team45Application {

    public static void main(String[] args) {
        SpringApplication.run(Team45Application.class, args);
    }
}
