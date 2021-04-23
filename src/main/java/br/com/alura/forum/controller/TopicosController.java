package br.com.alura.forum.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

import java.net.URI;
import java.util.Arrays;
import java.util.List;


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
	@GetMapping
	public List<TopicoDto> lista( String nomeCurso ){
		if ( nomeCurso == null ) {
			List<Topico> topicos = topicoRepository.findAll();
			
			return TopicoDto.converter(topicos);
		}
		
		// Trocamos para metodo do repository
		List<Topico> topicos = topicoRepository.findByCursoNome( nomeCurso );
		return TopicoDto.converter(topicos);
		
	}
	
	// Quando os parametros serão no corpo da requisição é necessário avisar o spring usamos a anotattion @RequestBody
	// Forma de chamar via POST
	// para mudar o status code devemos responder com um tipo de ReponseEntity
	// Devemos retornar uma uri no cabeçalho do Reponse de criado, e para isso usamos o uriComponentsBuilder que é do Spring
	// Recebemos um TopicoForm para não usar a nossa classe de dominio
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar( @RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		// criamos uma uri dinamica
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		// Retornamos no header a url de onde foi criado e no corpo o item criado
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
}
