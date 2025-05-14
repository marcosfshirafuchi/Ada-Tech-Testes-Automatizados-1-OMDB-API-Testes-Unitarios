package com.adatech.IMDB.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ada Tech - Projeto de Programação Web 2 : OMDB API")
                        .version("v1.0")
                        .description("Esta é uma API INCRÍVEL desenvolvida em grupo no módulo de web II do programa Desenvolva + da Ada Tech. "
                        + "Membros do projeto: Anderson Alves Santos, Janaína Cruz e Marcos Ferreira Shirafuchi")
                        .contact(new Contact()
                                .name("Ada Tech - Desenvolva + : API REST")
                                .url("https://github.com/marcosfshirafuchi/Ada-Tech-Programacao-Web-2"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}