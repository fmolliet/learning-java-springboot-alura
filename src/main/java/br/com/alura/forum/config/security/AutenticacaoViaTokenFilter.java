package br.com.alura.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

//  O filtro deve ser registrado via método addFilterBefore, na classe SecurityConfigurations. nao existe anotacao para o spring
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		String token = recuperarToken(request);
		
		boolean valido = tokenService.isTokenValido(token);
		
		System.out.println(valido);
		
		// Como se fosse o Next do Express
		filterChain.doFilter(request, response);
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if ( token == null || token.isEmpty() || ! token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
