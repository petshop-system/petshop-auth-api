package com.petshop.auth.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.adapter.output.repository.cache.SenhaRedisRepository;
import com.petshop.auth.adapter.output.repository.database.SenhaDatabaseRepository;
import com.petshop.auth.adapter.output.repository.database.SenhaJPARepository;
import com.petshop.auth.application.port.output.repository.SenhaCacheRepository;
import com.petshop.auth.application.port.output.repository.SenhaDatabaseRespository;
import com.petshop.auth.application.service.SenhaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenhaConfiguration {

    @Bean
    SenhaDatabaseRespository senhaDatabaseRespository (SenhaJPARepository senhaJPARepository) {
        return new SenhaDatabaseRepository(senhaJPARepository);
    }

    @Bean
    SenhaCacheRepository senhaCacheRepository(SenhaRedisRepository senhaRedisRepository,
                                              ObjectMapper objectMapper){
        return new com.petshop.auth.adapter.output.repository.cache.SenhaCacheRepository(senhaRedisRepository, objectMapper);
    }

    @Bean
    SenhaService senhaService (SenhaDatabaseRespository senhaDatabaseRespository,
                               SenhaCacheRepository senhaCacheRepository) {
        return new SenhaService(senhaCacheRepository, senhaDatabaseRespository);
    }
}
