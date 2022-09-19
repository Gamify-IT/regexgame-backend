package de.unistuttgart.finitequizbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages={
        "de.unistuttgart"})
@EnableFeignClients
public class FinitequizServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(FinitequizServiceApplication.class, args);
  }
}
