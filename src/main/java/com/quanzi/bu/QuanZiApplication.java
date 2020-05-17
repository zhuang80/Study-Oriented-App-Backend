package com.quanzi.bu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ChrisChen
 */
@SpringBootApplication
@EnableSwagger2
@EnableTransactionManagement
public class QuanZiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(QuanZiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}

