package com.example.demo.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class ApiDeprecationHandler extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (request.getRequestURI().startsWith("/v1/")) {
			response.addHeader("X-GftFood-Deprecated", "<strong> Essa versão da API está depreciadae deixará de existir a partir de 01/01/2022"
					+ "Use a versão mais atual da API.");
		}
		 return true;
	}
}
