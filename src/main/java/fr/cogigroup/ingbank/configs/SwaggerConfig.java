package fr.cogigroup.ingbank.configs;


import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder()
                .group("Retail-Bank-Account-Management")
                .packagesToScan("fr.cogigroup.ingbank.controllers")
                .pathsToMatch("/**")
                .build();
    }

}