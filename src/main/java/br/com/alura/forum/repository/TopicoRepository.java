package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.forum.modelo.Topico;

// Criamos essa interface que vai herdar todos metodos do JpaRepository (Seria como um DAO com os metodos prontos no JPA)
public interface TopicoRepository extends JpaRepository<Topico, Long> {
	// Aqui eu falo que vai chamar pelo atribuito Curso da classe, e concateno o atributo da outra classe, o nome
	// Caso tenha ambiguidade usamos findByCurso_Nome para chamar atribuito de outra classe e não da propria
	Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);
	
	// Caso vc nao queria usar o padrão do JPA vc precisa usar a anotação query e vai ter que gerar a query manualmente com JPQL
	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
}
