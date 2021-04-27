package br.com.alura.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
 * Criaremos uma classe de segurança pois muita coisa é dinamica e para cada projeto
 * Nao adicionamos na main do projeto o @EnableWebSecurity
 * E para dizer para o spring que é uma configuração usamos a anotação de @Configuration
 * Herdamos da classe WebSecurityConfigurerAdapter
 * Só fazendo isso, já está habilitado. O spring por padrão bloqueia todo acesso a API
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

}
