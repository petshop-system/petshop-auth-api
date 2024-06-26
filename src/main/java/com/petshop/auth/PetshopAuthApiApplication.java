package com.petshop.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PetshopAuthApiApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value(value = "${spring.config.activate.on-profile}")
	private String activeProfile;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PetshopAuthApiApplication.class);
		app.run();
	}

	@Bean("objectMapper")
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		return objectMapper.registerModule(javaTimeModule);
	}

	@Override
	public void run(String... args) {

		logger.info("#############################################################");
		logger.info("active profile: {}", activeProfile);
		logger.info("#############################################################");

	}
}
