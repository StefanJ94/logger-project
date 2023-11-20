package com.example.LoggerProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfiguration {

	private ApiInfo apiInfo() {
		return new ApiInfo("Log app",
				"ITBC assignment",
				"1.0",
				"Terms of service",
				new Contact("Stefan Jovanovic", "", "jovanovic.stefan94@yahoo.com"),
				"License of API",
				"API license URL",
				Collections.emptyList());
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30)
				.apiInfo(apiInfo())
				.securitySchemes(List.of(new ApiKey("JWT", "Authorization", "header")))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.itbcloggerfinalproject"))
				.paths(PathSelectors.any())
				.build();
	}
}
