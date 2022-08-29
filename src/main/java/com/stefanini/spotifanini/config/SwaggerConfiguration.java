package com.stefanini.spotifanini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Spotifanini Documentation")
                .description("Documentation for Spotifanini API")
                .license("Apache License 2.0")
                .licenseUrl("https://github.com/MateusFS99/Spotifanini-SpringBoot/blob/main/LICENSE")
                .version("1.0.0")
                .build();

        return apiInfo;
    }
}
