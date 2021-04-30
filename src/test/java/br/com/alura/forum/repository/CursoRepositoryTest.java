package br.com.alura.forum.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.alura.forum.modelo.Curso;


@RunWith(SpringRunner.class)
//A anotação @DataJpaTest simplifica a escrita de testes automatizados de interfaces Repository.
//com o controle transacional automático e para permitir a utilização do EntityManager de testes do próprio Spring.
@DataJpaTest
//PARA CONFIGURACAO DE BANCO EXTERNO
// Precisamos utilizar essa anotação para que o Spring não considere que os testes devem sempre ser executados
// utilizando um banco de dados em memória.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CursoRepositoryTest {
	
	@Autowired
	private CursoRepository repository;
	
	@Autowired
	private TestEntityManager em;

	@Test
	public void deveriaCarregarUmCursoAoBuscarPeloSeuNome() {
		String nomeCurso = "HTML 5";
		
		Curso html5 = new Curso();
		html5.setNome(nomeCurso);
		html5.setCategoria("Programação");
		
		em.persist(html5);
		
		Curso curso = repository.findByNome(nomeCurso);
		
		Assert.assertNotNull(curso);
		
		Assert.assertEquals(nomeCurso, curso.getNome());
	}
	
	@Test
	public void naoDeveriaCarregarUmCursoAoBuscarPeloSeuNome() {
		String nomeCurso = "JPA";
		
		Curso curso = repository.findByNome(nomeCurso);
		
		Assert.assertNull(curso);
	}


}
