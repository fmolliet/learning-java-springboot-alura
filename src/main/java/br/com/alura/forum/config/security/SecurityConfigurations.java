package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
 * Criaremos uma classe de segurança pois muita coisa é dinamica e para cada projeto
 * Nao adicionamos na main do projeto o @EnableWebSecurity
 * E para dizer para o spring que é uma configuração usamos a anotação de @Configuration
 * Herdamos da classe WebSecurityConfigurerAdapter
 * Só fazendo isso, já está habilitado. O spring por padrão bloqueia todo acesso a API
 */
@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	/*
	 * Adicionamos como injeção de dependencia
	 */
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	// A classe AuthenticationManager não é possivel fazer injeção de dependencia pois nao vem configurada para isso
	// Adicionamos @Bean para indicar que ele retorna o AuthenticationManager 
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	/*
	 * Esse metodo controla a autentificação
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// usamos o metodo userDetailsService para dizer ao spring qual é classe que tem a lógica de autentificação
		auth.userDetailsService( autenticacaoService )
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	
	/*
	 * Configuração de autorização: URL, quem pode acessar cada URL, perfil de acesso
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/topicos").permitAll() // Somente iremos liberar o metodo GET
			.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
			.antMatchers(HttpMethod.POST, "/auth").permitAll()
			// Usamos o AnyRequest().authenticated() para dizer ao spring que todas requests nao mapeadas acima precisam de autentificação
			.anyRequest().authenticated()
			.and().csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			// Adicionamos antes o filtro que vai realizar a autentificacao do JWT antes de username e senha
			.and().addFilterBefore(new AutenticacaoViaTokenFilter( tokenService ), UsernamePasswordAuthenticationFilter.class);
			// Para usar o formulario padrao para autentificação do spring .and().formLogin();
			//Assim libera todos os metodos do "/topicos" .antMatchers("/topicos").permitAll();
	}
	
	/*
	 * Configuração de recursos estáticos (JS, CSS, HTML)
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}
	
}
