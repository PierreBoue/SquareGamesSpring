package com.macaplix.squareGames;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;

@EntityScan
@SpringBootApplication
public class SquareGamesApplication
{

	public static void main(String[] args)
	{

		SpringApplication.run(SquareGamesApplication.class, args);
	}

}
