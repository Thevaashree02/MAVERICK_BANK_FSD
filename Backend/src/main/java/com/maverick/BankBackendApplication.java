package com.maverick;

import com.maverick.entity.User;
import com.maverick.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankBackendApplication.class, args);
    }

    /*@Bean
    CommandLineRunner createAdmin(UserService userService) {
        return args -> {
            if (!userService.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin"); // raw password
                admin.setRole("ADMIN");
                userService.signUp(admin);  // this encodes the password
            }
        };
    }*/

}
