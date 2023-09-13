    package com.example.petshopback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@SpringBootApplication
@MapperScan("com.example.petshopback.mapper")
public class PetshopBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetshopBackApplication.class, args);
    }

}
