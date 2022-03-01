package com.qiao.jwtall;

import com.qiao.jwtall.dto.UserDTO;
import com.qiao.jwtall.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * https://stackoverflow.com/questions/21978658/invalidating-json-web-tokens
 */
@SpringBootApplication
public class JwtAllApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAllApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserService userService) {
		return args -> {
			UserDTO userDTO = new UserDTO();
			userDTO.setUsername("admin");
			userDTO.setPassword("admin");
			userDTO.setEmail("admin@gmail.com");
			userService.create(userDTO);
		};
	}

}
