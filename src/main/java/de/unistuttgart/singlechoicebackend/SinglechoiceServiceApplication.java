package de.unistuttgart.singlechoicebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SinglechoiceServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SinglechoiceServiceApplication.class, args);
  }
}
