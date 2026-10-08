package com.ktdsuniversity.edu.commons.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ktdsuniversity.edu.commons.beans.interceptors.CheckSessionInterceptor;

/**
 * 분산되어 있는 spring boot 설정을들 class를 통해서 한곳에 모은다
 * 인터셉터 등록을 위한 Spring Boot 설정 클래스 
 */

@Configuration
public class WebConfig  implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new CheckSessionInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns(
						"/members/list",
						"/error",
						"/members/login",
					    "/members/signup",
					    "/articles/list",
					    "/articles/{articleId}",
					    "/articles/{articleId}/replies/list")
				;
		
	}
}
