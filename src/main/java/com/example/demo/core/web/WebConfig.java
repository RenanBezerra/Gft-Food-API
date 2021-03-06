package com.example.demo.core.web;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//	@Autowired
//	private ApiRetirementHandler apiRetirementHandler;

	@Bean
	public Filter shalowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}
}
