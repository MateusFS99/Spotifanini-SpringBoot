package com.stefanini.spotifanini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class SpotifaniniApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifaniniApplication.class, args);
	}

}
