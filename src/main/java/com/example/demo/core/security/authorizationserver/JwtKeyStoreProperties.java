package com.example.demo.core.security.authorizationserver;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Validated
@Component
@ConfigurationProperties("gftfood.jwt.keystore")
public class JwtKeyStoreProperties {

	@NotNull
	private Resource jksLocation;

	@NotBlank
	private String password;

	@NotBlank
	private String keypairAlias;


}
