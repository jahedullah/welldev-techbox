package com.welldev.TechBox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TechBoxApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechBoxApplication.class, args);
	}

}
