package br.com.alura.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.AuthenticationException;

import br.com.alura.forum.config.security.TokenService;
import br.com.alura.forum.controller.dto.TokenDto;
import br.com.alura.forum.controller.form.LoginForm;

/*
 * 
 */
@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	
	// A classe AuthenticationManager deve ser utilizada apenas na lógica de autenticação via username/password, para a geração do token.
	@Autowired
	private AuthenticationManager authManager;
	
	// O autowired ele procura um Beam para injetar
	@Autowired
	private TokenService tokenServices;
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar( @RequestBody @Valid LoginForm form ){
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenServices.gerarToken(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e ) {
			return ResponseEntity.badRequest().build();
		}
	}
}
