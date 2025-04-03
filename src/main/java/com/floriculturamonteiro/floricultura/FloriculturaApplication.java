package com.floriculturamonteiro.floricultura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FloriculturaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FloriculturaApplication.class, args);
	}

}
