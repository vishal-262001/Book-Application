package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import com.example.demo.role.Role;
import com.example.demo.role.RoleRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	
			}
	@Bean
	public CommandLineRunner runner(RoleRepository rolerepo) {
	    return args -> {
	        // Ensure the repository method matches your Role entity's field.
	        if (rolerepo.findByName("USER").isEmpty()) {
	            rolerepo.save(Role.builder().name("USER").build());
	        }
	    };
	}

}
