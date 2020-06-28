package com.brightcoding.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {	//Methode permet de autoriser les client à accéder users methodes via les routes mentionnées
//		registry
//		.addMapping("users")
//		.allowedMethods("GET","POST","PUT")
//		.allowedOrigins("http://localhost:4200");
//		
		registry
		.addMapping("/**")
		.allowedMethods("*")
		.allowedOrigins("*");	//Laissez tous pour faire tous
	}

}
