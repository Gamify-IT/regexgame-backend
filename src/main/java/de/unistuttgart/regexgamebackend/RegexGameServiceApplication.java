package de.unistuttgart.regexgamebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RegexGameServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(RegexGameServiceApplication.class, args);
    }
}
