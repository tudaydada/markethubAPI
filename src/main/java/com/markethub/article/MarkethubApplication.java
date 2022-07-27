package com.markethub.article;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@OpenAPIDefinition
@RestController
public class MarkethubApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarkethubApplication.class, args);
    }

}
