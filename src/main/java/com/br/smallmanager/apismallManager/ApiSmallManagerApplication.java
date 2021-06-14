package com.br.smallmanager.apismallManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport; 
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableSwagger2
public class ApiSmallManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSmallManagerApplication.class, args);
	}

}
