package br.com.alura.forum.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.alura.forum.modelo.Usuario;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations {

	/*
	 * A configuração é feita para qual endpoints ele vai fazer uma análise
	 * Para acessar a documentação da API, devemos entrar no endereço http://localhost:8080/swagger-ui.html;
	 */
	@Bean
	public Docket forumApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))
				.paths(PathSelectors.ant("/**"))
				.build()
				// Como nossa classe Usuario possui atributos relacionados ao login, senha e perfis de acesso,
				// não é recomendado que essas informações sejam expostas na documentação do Swagger.
				.ignoredParameterTypes(Usuario.class)
				// Agora informamos que é necessario adicionar no header na documentação do swagger
				// Sem esse header não será possível testar os endpoints que exigem autenticação pela interface do Swagger.
				.globalOperationParameters(Arrays.asList(
	                    new ParameterBuilder()
	                    .name("Authorization")
	                    .description("Header para token gerar o token JWT")
	                    // Tipo do parametro
	                    .modelRef(new ModelRef("string"))
	                    // Onde vai o parametro
	                    .parameterType("header")
	                    .required(false)
	                    .build()));
	}
}
