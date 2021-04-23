package br.com.alura.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Criamos essa classe para lidar com os erros de validação do beam validation
@RestControllerAdvice
public class ErroValidacaoHandler {
	
	// Para internacionalização das mensagens
	@Autowired
	private MessageSource messageSource;
	
	// Precisamos falar para o spring que ele vai chamar esse metodo e vai receber uma exceção
	// Se nao informar qual foi o @ResponseStatus entao ele vai retornar 200, por isso colocamos para enviar 400
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorDeFormularioDto> handle(MethodArgumentNotValidException exception ) {
		// Cria um array List vazio
		List<ErrorDeFormularioDto> dto = new ArrayList<>();
		// Extrai uma lista de erros dos campos que deram erro na validação
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		// Para cada erro ele irá traduzir o erro e adicionar na lista.
		// Exemplo de lambda
		fieldErrors.forEach( e-> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErrorDeFormularioDto erro = new ErrorDeFormularioDto(e.getField(), mensagem);
			dto.add(erro);
		});
		
		return dto;
	}
}
