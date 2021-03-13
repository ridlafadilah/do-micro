package com.dongkap.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigApplication extends SpringBootServletInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigApplication.class);
	
    public static void main(String[] args) {
	    SpringApplication.run(ConfigApplication.class, args);
        LOG.info("Dongkap!");
    }

}
