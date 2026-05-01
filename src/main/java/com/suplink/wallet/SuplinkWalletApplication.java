package com.suplink.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SuplinkWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuplinkWalletApplication.class, args);
    }
}