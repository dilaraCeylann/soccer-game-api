package com.dilaraceylan.soccergame.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {     

	
	 public static final String AUTHORIZATION_HEADER = "Authorization";

	    @Bean
	    public Docket swaggerSpringfoxDocket() {
	       
	        Docket docket = new Docket(DocumentationType.SWAGGER_2)
	            .apiInfo(apiInfo())
	            .apiInfo(ApiInfo.DEFAULT)
	            .forCodeGeneration(true)
	            .genericModelSubstitutes(ResponseEntity.class)
	            .ignoredParameterTypes(Pageable.class)
	            .ignoredParameterTypes(java.sql.Date.class)
	            .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
	            .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
	            .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
	            .securityContexts(new ArrayList<SecurityContext>(Arrays.asList(securityContext())))
	            .securitySchemes(Collections.singletonList(apiKey()))
	            .useDefaultResponseMessages(false);
	            

	        docket = docket
	        		.select()
	        		.apis(RequestHandlerSelectors.any())
		            .paths(PathSelectors.any())   
		            .build();

	        return docket;
	    }
	    
	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("REST API Document")
	                .termsOfServiceUrl("localhost")
	                .version("1.0")
	                .build();
	    }


	    private ApiKey apiKey() {
	        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	    }

	    private SecurityContext securityContext() {
	        return SecurityContext.builder()
	            .securityReferences(defaultAuth())
	            .build();
	    }

	    List<SecurityReference> defaultAuth() {	
	        AuthorizationScope authorizationScope
	            = new AuthorizationScope("global", "accessEverything");      
	        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	        authorizationScopes[0] = authorizationScope;
	        SecurityReference securityReference = new SecurityReference("JWT", authorizationScopes);
	        return new ArrayList<SecurityReference>(Arrays.asList(securityReference));
	    }
}
