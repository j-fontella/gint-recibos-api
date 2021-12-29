package com.ginc.geradorrecibo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(scanBasePackages = "com.ginc.geradorrecibo", exclude = {SecurityAutoConfiguration.class})
@EntityScan(basePackages = "com.ginc.geradorrecibo.models")
public class GeradorReciboApplication {
	public static void main(String[] args) {
		SpringApplication.run(GeradorReciboApplication.class, args);
	}
}
