package com.example.Foro_Hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.Foro_Hub.domain")
//@EnableJpaRepositories(basePackages = "com.example.Foro_Hub.domain.respuesta")
//@EntityScan(basePackages = "com.example.Foro_Hub.domain.usuario")
public class ForoHubApplication {
	public static void main(String[] args) {
		SpringApplication.run(ForoHubApplication.class, args);
	}
}