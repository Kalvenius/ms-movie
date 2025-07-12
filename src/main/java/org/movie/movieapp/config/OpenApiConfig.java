package org.movie.movieapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI movieDatabaseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Movie Database API")
                        .description("A comprehensive RESTful API for managing movie database with CRUD operations")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Movie Database Team")
                                .email("support@moviedb.com")
                                .url("https://moviedb.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}