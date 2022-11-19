package com.financeiroAPI.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice //anotação que faz monitoramento e captura das excessoes
public class FinanceiroExceptionHandler extends ResponseEntityExceptionHandler {// Extensão que captura excessões de respostas de entidades
	
	@Autowired
	private MessageSource messageSource; //injeção de dependencia desse objeto para captura das mensagens no arquivo messages.properties
	
	//capturando mensagens que não conseguiram ler ou ser lidas
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
	String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());//variavel contendo mensgem do usuario criada no arquivo messages.properties e capturada pelo objeto Message Source 
	String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : toString(); //captura a excessão do sistema para o Desenvolvedor	
	
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, status.BAD_REQUEST, request);//retorno passando a mensagem do usuario e para o Desenvolvedor
	}
	
	@Override
		protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
				HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Erro> erros = criarListadeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros,headers, status.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object>nhandleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		
		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());//variavel contendo mensgem do usuario criada no arquivo messages.properties e capturada pelo objeto Message Source 
		String mensagemDesenvolvedor = ex.toString(); //captura a excessão do sistema para o Desenvolvedor
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		
	}
	
	//Criando uma lista de erros
	private List<Erro> criarListadeErros(BindingResult bindingResult){
	
		List<Erro> erros = new ArrayList<>();
		
			for(FieldError fieldError : bindingResult.getFieldErrors()) {
				String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
				String mensagemDesenvolvedor = fieldError.toString();
				erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}
			
		return erros;
	}
	
	//Criando uma classe para poder usar as a mensagem de usuario e mensagem do desenvolvedor no retorno acima
	public static class Erro{
		//variaves para construção do construtor
		private String mensagemUsuario;
		private String mensagenDesenvolvedor;
		//construtor recebendo parametros e passando para as variaveis acima
		public Erro(String mensagemUsuario, String mensagenDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagenDesenvolvedor = mensagenDesenvolvedor;
		}
		
		//geracao dos getters pra ter acesso ao conteudo das variaveis

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagenDesenvolvedor() {
			return mensagenDesenvolvedor;
		}
		
		
	}
	
	

}
