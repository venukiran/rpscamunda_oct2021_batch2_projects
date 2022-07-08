package com.boa.foodyapi;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableProcessApplication("foodyapi")
public class FoodKafkaSagaCamundaApplication {

  public static void main(String... args) {
    SpringApplication.run(FoodKafkaSagaCamundaApplication.class, args);
  }
  @Bean
  public RestTemplate getRestTemplate() {
	  return new RestTemplate();
  }
  
 
}

