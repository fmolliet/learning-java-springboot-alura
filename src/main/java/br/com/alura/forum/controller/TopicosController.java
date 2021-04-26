package br.com.alura.forum.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;


// QUando adicionado Request Mapping /topicos na classe ele diz que todos os metodos serão para essa rota
@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	// Quando adicionado indica ao Spring que ele vai retornar como response 
	// @ResponseBody se for um controller tudo que vai retornar precisa dele mas se for um @RestController não é necessário
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	
	@Autowired
	private CursoRepository cursoRepository;
	// Forma alternativa de adicionar a rota e o metodo
	//@RequestMapping(value ="/topicos", method = RequestMethod.GET)
	// Forma de chamar via GET
	// @@RequestParam serve para dizer que os params vem na urls
	// Usamos @PageableDefault(sort="id", direction = Direction.DESC) para definir a paginacao default quando ele não recebe o padrão
	@GetMapping
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,
			@PageableDefault(sort="id", direction = Direction.DESC, page=0, size = 10) Pageable paginacao ){
		
		// Pageable do spring framework.data
		// Usamos A implementacao do metodo of que passamos a direção da ordenação e qual atribuito vai ser ordenado
		// Pageable paginacao = PageRequest.of(pagina, qtd, Direction.DESC, ordenacao);
		
		if ( nomeCurso == null ) {
			// Quando passamos para o spring uma paginação ele retorna Page nao uma lista
			Page<Topico> topicos = topicoRepository.findAll(paginacao);
			
			return TopicoDto.converter(topicos);
		}
		
		// Trocamos para metodo do repository
		Page<Topico> topicos = topicoRepository.findByCursoNome( nomeCurso, paginacao );
		return TopicoDto.converter(topicos);
		
	}
	
	// Quando os parametros serão no corpo da requisição é necessário avisar o spring usamos a anotattion @RequestBody
	// Forma de chamar via POST
	// para mudar o status code devemos responder com um tipo de ReponseEntity
	// Devemos retornar uma uri no cabeçalho do Reponse de criado, e para isso usamos o uriComponentsBuilder que é do Spring
	// Recebemos um TopicoForm para não usar a nossa classe de dominio
	// Usamos @Valid para validar o form recebido com o BeamValidation
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar( @Valid @RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {		
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		// criamos uma uri dinamica
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		// Retornamos no header a url de onde foi criado e no corpo o item criado
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	// Quando parte da URL é dinamica e para nao criar conflito com outro metodo GET passamos de parametro 
	// Precisamos avisar para o spring que vira na url nao como query params usamos para isso o @PathVariable
	// trocamos o DTO para DetalhesDoTopicoDto pois iremos trazer outros dados
	// Alteramos o retorno de DetalhesDoTopicoDto para ResponseEntity<DetalhesDoTopicoDto> assim, podemos tratar o retorno
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id ) { 
		// findById retorna um objeto Optional<> e ele tem um metodo isPresent() e um metodo Get
		Optional<Topico> topico = topicoRepository.findById(id);
		if ( topico.isPresent() ) {
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	// Put serve para sobrescrever, o patch é uma pequena atualização
	// Adicionamos @Transctional para comitar alteração após realizar transação
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar( @PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form ){
		Optional<Topico> optional = topicoRepository.findById(id);
		if ( optional.isPresent() ) {
			Topico topico = form.atualizar(id, topicoRepository );
			
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	// Utilizamos DeleteMappinga para uso do metodo HTTP Delete, adicionamos validação para caso nao encontre
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Topico> optional = topicoRepository.findById(id);
		if ( optional.isPresent() ) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
