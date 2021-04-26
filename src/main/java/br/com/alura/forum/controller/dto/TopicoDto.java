package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.alura.forum.modelo.Topico;

public class TopicoDto {
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	
	// A classe de DTO Serve para controlar quais informações vão poder ser acessadas ( DTO )
	public TopicoDto( Topico topico ) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
	}
	
	public Long getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	// Usamos as libs de Stream para iterar para cada topico e instanciar o topico DTO retornando para lista
	public static Page<TopicoDto> converter(Page<Topico> topicos) {
		// Quando trocamos de list para page iremos tirar o metodo stream
		return topicos.map(TopicoDto::new);
	}
}
