package com.example.demo.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.api.exceptionhandler.Problem;
import com.fasterxml.classmate.TypeResolver;

import lombok.var;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

	@Bean
	public Docket apiDocket() {
		
		var typeResolver = new TypeResolver();	
		
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.example.demo.api")).paths(PathSelectors.any()).build()
				.useDefaultResponseMessages(false).globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
				.globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages()).apiInfo(apiInfo())
				.additionalModels(typeResolver.resolve(Problem.class))
				.tags(new Tag("Cidades", "Gerencia as cidades"),
						new Tag("Grupos","Gerencia os grupos de usuarios"));
		}

	private List<ResponseMessage> globalGetResponseMessages() {
		return Arrays.asList(

				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Erro interno do servidor")
						.responseModel(new ModelRef("Problema"))
						.build(),
				new ResponseMessageBuilder().code(HttpStatus.NOT_ACCEPTABLE.value())
						.message("Recurso não possui representaçãp que poderia ser aceita pelo consumidor").build());
	}

	private List<ResponseMessage> globalPostPutResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value())
						.message("Requisiçãp invalida (erro cliente)")
						.responseModel(new ModelRef("Problema"))
						.build(),
				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Erro interno do servidor")
						.responseModel(new ModelRef("Problema"))
						.build(),
				new ResponseMessageBuilder().code(HttpStatus.NOT_ACCEPTABLE.value())
						.message("Recurso não possui representação que poderia ser aceita pelo consumidor").build(),
				new ResponseMessageBuilder().code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
						.message("Requisição recusada porque o corpo está em um formato não suportado")
						.responseModel(new ModelRef("Problema"))
						.build());
	}

	private List<ResponseMessage> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value())
						.message("Requisição invalida (erro do cliente)")
						.responseModel(new ModelRef("Problema"))
						.build(),
				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Erro interno no servidor")
						.responseModel(new ModelRef("Problema"))
						.build());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("GftFood API").description("API aberta para clientes e restaurantes")
				.version("1").contact(new Contact("GFT", "https://www.gft.com", "contato@gft.com")).build();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
