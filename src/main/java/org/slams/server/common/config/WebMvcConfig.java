package org.slams.server.common.config;

import org.slams.server.common.config.resolver.UserIdResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${slam.cors.allowedOrigins}")
	private List<String> allowedOrigins;
	private UserIdResolver userIdResolver;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/v1/**")
			.allowedOrigins(allowedOrigins.toArray(new String[0]))
			.allowedMethods("*")
			.maxAge(3600);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(userIdResolver);
	}


}