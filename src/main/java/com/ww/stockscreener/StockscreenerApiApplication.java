package com.ww.stockscreener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class StockscreenerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockscreenerApiApplication.class, args);
    }

}
