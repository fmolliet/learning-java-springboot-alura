package br.com.alura.forum.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

import java.util.Arrays;
import java.util.List;


@RestController
public class TopicosController {
	
	// Quando adicionado indica ao Spring que ele vai retornar como response 
	// @ResponseBody se for um controller tudo que vai retornar precisa dele mas se for um @RestController não é necessário
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@RequestMapping("/topicos")
	public List<TopicoDto> lista( String nomeCurso ){
		if ( nomeCurso == null ) {
			List<Topico> topicos = topicoRepository.findAll();
			
			return TopicoDto.converter(topicos);
		}
		
		// Trocamos para metodo do repository
		List<Topico> topicos = topicoRepository.findByCursoNome( nomeCurso );
		return TopicoDto.converter(topicos);
		
	}
}
