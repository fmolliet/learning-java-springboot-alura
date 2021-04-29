package br.com.alura.forum;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;

//A anotação @SpringBootTest serve para que o Spring inicialize o servidor e carregue os beans da aplicação durante a execução dos testes automatizados.
@SpringBootTest
class ForumApplicationTests {

	@Test
	void contextLoads() {
		Assert.assertTrue(true);
	}

}
