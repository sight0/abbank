package com.ooplab.abbank;

import com.ooplab.abbank.dao.BankAccountRepository;
import com.ooplab.abbank.dao.UserRepository;
import com.ooplab.abbank.service.BankerService;
import com.ooplab.abbank.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AbbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbbankApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(BankAccountRepository bankAccountRepository) {
		return args -> {

		};
	}

}
