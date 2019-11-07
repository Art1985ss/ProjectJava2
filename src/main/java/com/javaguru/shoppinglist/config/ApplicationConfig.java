package com.javaguru.shoppinglist.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
@ComponentScan(basePackages = "com.javaguru.shoppinglist")
public class ApplicationConfig {
    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}
