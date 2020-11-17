package com.example.demo.core.springfox;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.api.exceptionhandler.Problem;
import com.example.demo.api.model.CozinhaModel;
import com.example.demo.api.model.PedidoResumoModel;
import com.example.demo.api.openapi.model.CozinhasModelOpenApi;
import com.example.demo.api.openapi.model.PageableModelOpenApi;
import com.example.demo.api.openapi.model.PedidosResumoModelOpenApi;
import com.fasterxml.classmate.TypeResolver;

import lombok.var;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
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
				.ignoredParameterTypes(ServletWebRequest.class,
						URL.class,URI.class,URLStreamHandler.class,Resource.class, File.class, InputStream.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Page.class, CozinhaModel.class),
						CozinhasModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(Page.class, PedidoResumoModel.class), PedidosResumoModelOpenApi.class))
				.apiInfo(apiInfo())
				.tags(new Tag("Cidades", "Gerencia as cidades"), new Tag("Grupos", "Gerencia os grupos de usuarios"),
						new Tag("Cozinhas", "Gerencia as cozinhas"),
						new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
						new Tag("Pedidos", "Gerencia os produtos"),
						new Tag("Restaurantes", "Gerencia os restaurantes"),
						new Tag("Estados","Gerencia os estados"),
						new Tag("Produtos", "Gerencia os produtos de restaurantes"),
						new Tag("Usuários", "Gerencia os usuários"),
						new Tag("Estatisticas", "Estatisticas da AlgaFood"));
	}

	private List<ResponseMessage> globalGetResponseMessages() {
		return Arrays.asList(

				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Erro interno do servidor").responseModel(new ModelRef("Problema")).build(),
				new ResponseMessageBuilder().code(HttpStatus.NOT_ACCEPTABLE.value())
						.message("Recurso não possui representaçãp que poderia ser aceita pelo consumidor").build());
	}

	private List<ResponseMessage> globalPostPutResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value())
						.message("Requisiçãp invalida (erro cliente)").responseModel(new ModelRef("Problema")).build(),
				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Erro interno do servidor").responseModel(new ModelRef("Problema")).build(),
				new ResponseMessageBuilder().code(HttpStatus.NOT_ACCEPTABLE.value())
						.message("Recurso não possui representação que poderia ser aceita pelo consumidor").build(),
				new ResponseMessageBuilder().code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
						.message("Requisição recusada porque o corpo está em um formato não suportado")
						.responseModel(new ModelRef("Problema")).build());
	}

	
	private List<ResponseMessage> globalDeleteResponseMessages() {
		return Arrays.asList(new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value())
				.message("Requisição invalida (erro do cliente)").responseModel(new ModelRef("Problema")).build(),
				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Erro interno no servidor").responseModel(new ModelRef("Problema")).build());
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