package br.com.alura.forum.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// Adaptação de imports por causa da versão https://cursos.alura.com.br/forum/topico-spring-boot-v-2-4-2-142404
@ExtendWith(SpringExtension.class)
//  Eu vou colocar uma anotação chamada @WebMvcTest. Quando vamos testar a camada Controller, 
// na verdade temos que utilizar essa anotação. Assim o Spring carrega no contexto da aplicação só as classes da parte MVC. 
// Então só os Controllers, RestController, ControllerAdvice, tudo que tem a ver com a camada MVC do projeto.
//@WebMvcTest
@SpringBootTest
// Para conseguir injetar o MockMvc na classe de testes e necessário anotá-la com @AutoConfigureMockMvc.
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AutenticacaoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void deveriaDevolver400CasoDadosDeAutenticacaoEstejaIncorretos() throws Exception {
		URI uri = new URI("/auth");
		String json = "{\"email\": \"moderador@email.com\",\"senha\": \"123456\"}";
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.post(uri)
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(400));
	}

}
