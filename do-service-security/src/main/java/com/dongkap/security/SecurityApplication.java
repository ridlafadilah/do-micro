package com.dongkap.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dongkap.security.configuration.ApplicationProperties;

@SpringBootApplication(scanBasePackages={"com.dongkap"})
@EnableAsync
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableResourceServer
@EnableCircuitBreaker
@EnableConfigurationProperties(ApplicationProperties.class)
@EnableFeignClients(basePackages = {"com.dongkap.feign"})
@EnableJpaRepositories(basePackages = { "com.dongkap.*.dao", "com.dongkap.*.service" })
public class SecurityApplication extends SpringBootServletInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(SecurityApplication.class);
	
    public static void main(String[] args) {
	    SpringApplication.run(SecurityApplication.class, args);
        LOG.info("Dongkap!");
    }

}
