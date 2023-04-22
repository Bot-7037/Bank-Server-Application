package com.cg.bankapp.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration class to configure swagger UI to perform get and post on
 * localhost
 * 
 * @author himanegi
 *
 */
@Configuration
@EnableSwagger2
public class BankAppSwaggerConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public Docket swaggerConfig() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.cg.bankapp")).build().apiInfo(getApiMetaData());
	}

	private ApiInfo getApiMetaData() {
		return new ApiInfo("BankApp", "Bank App REST API", "1.0", "Marine Corp Project",
				new springfox.documentation.service.Contact("Himanshu Negi", "www.capgemini.com",
						"himanshu.c.negi@capgemini.com"),
				"API License", "http:capgemini.com", Collections.emptyList());
	}
}
