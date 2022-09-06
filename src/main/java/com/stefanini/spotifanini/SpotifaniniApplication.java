package com.stefanini.spotifanini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
public class SpotifaniniApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifaniniApplication.class, args);
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public OpenAPI customOpenAPI() {

		return new OpenAPI().components(new Components().addSecuritySchemes("bearer-key",
				new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
	}
}