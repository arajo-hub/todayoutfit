package com.ara.todayoutfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class BootTestApplication {

    public static void main(String[] args) {
        System.out.println("BootTestApplication.main");
        SpringApplication.run(BootTestApplication.class, args);
    }
}
