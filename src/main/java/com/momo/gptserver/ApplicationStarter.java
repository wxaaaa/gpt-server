package com.momo.gptserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ApplicationStarter {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = SpringApplication.run(ApplicationStarter.class, args);
    }
}
