package com.sparta.lv1_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Lv1TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(Lv1TestApplication.class, args);
	}

}
