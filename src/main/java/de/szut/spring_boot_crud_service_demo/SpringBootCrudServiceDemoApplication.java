package de.szut.spring_boot_crud_service_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootCrudServiceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCrudServiceDemoApplication.class, args);
	}

}
