package com.wequan.bu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ChrisChen
 */
@SpringBootApplication
@EnableSwagger2
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class WeQuanApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WeQuanApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}

