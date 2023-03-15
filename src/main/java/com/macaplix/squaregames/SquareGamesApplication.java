package com.macaplix.squaregames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
@SpringBootApplication
public class SquareGamesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SquareGamesApplication.class, args);
    }

}
