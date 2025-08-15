package com.example.tracking_service;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.tracking_service.repositoryJPA")
@EnableRedisRepositories(basePackages = "com.example.tracking_service.repositoryRedis")
public class RepositoryConfig {
}

