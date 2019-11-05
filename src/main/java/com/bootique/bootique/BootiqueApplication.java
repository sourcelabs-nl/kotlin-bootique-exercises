package com.bootique.bootique;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring boot application with Swagger2 enabled.
 */
@SpringBootApplication
@EnableSwagger2
public class BootiqueApplication {

    /**
     * Swagger2 configuration.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bootique.bootique"))
                .build();
    }

    /**
     * Runs the Spring boot application.
     */
    public static void main(String[] args) {
        SpringApplication.run(BootiqueApplication.class, args);
    }
}
