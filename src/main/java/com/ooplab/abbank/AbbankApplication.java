package com.ooplab.abbank;

import com.ooplab.abbank.dao.BankAccountRepository;
import com.ooplab.abbank.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication

public class AbbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbbankApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserService userService) {
		return args -> {
			//userService.setPassword("saeed", "saeed");
		};
	}

}
