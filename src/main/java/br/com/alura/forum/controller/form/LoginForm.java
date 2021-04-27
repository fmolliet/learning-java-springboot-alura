package br.com.alura.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class LoginForm {


	// Para realizar as validações no corpo recebido o JAVA utiliza o beam validation e usamos annotations em vez de if else
	@NotNull @NotEmpty @Length(min=5)
	private String email;
	@NotNull @NotEmpty @Length(min=5)
	private String senha;
	
	
	public void setEmail(String email) {
		this.email = email;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getEmail() {
		return email;
	}
	public String getSenha() {
		return senha;
	}
	
	
}
