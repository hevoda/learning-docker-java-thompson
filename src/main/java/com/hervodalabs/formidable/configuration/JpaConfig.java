package com.hervodalabs.formidable.configuration;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.hervodalabs.formidable.repositories")
@EntityScan(basePackages = "com.hervodalabs.formidable.domain")
@Profile("h2")
public class JpaConfig {
}
