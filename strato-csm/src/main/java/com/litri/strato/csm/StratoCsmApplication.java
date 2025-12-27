package com.litri.strato.csm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan("com.litri.strato")
@EnableAsync
@EnableCaching
@EnableScheduling
public class StratoCsmApplication {

	public static void main(String[] args) {
		SpringApplication.run(StratoCsmApplication.class, args);
	}

}
