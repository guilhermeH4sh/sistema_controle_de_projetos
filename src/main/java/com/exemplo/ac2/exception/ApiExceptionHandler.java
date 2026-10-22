package com.exemplo.ac2.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.exemplo.ac2.dto.ErroResponse;

@RestControllerAdvice
public class ApiExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<ErroResponse> naoEncontrado(RecursoNaoEncontradoException ex) {
		return responder(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(RegraNegocioException.class)
	public ResponseEntity<ErroResponse> regraDeNegocio(RegraNegocioException ex) {
		return responder(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErroResponse> corpoInvalido(HttpMessageNotReadableException ex) {
		return responder(HttpStatus.BAD_REQUEST,
				"Dados enviados em formato inválido. Verifique os campos e as datas (aaaa-mm-dd).");
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErroResponse> parametroInvalido(MethodArgumentTypeMismatchException ex) {
		return responder(HttpStatus.BAD_REQUEST, "Parâmetro inválido: " + ex.getName() + ".");
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErroResponse> integridade(DataIntegrityViolationException ex) {
		return responder(HttpStatus.CONFLICT, "Não foi possível salvar os dados informados.");
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErroResponse> erroInesperado(Exception ex) {
		log.error("Erro inesperado na API", ex);
		return responder(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno ao processar a requisição.");
	}

	private ResponseEntity<ErroResponse> responder(HttpStatus status, String mensagem) {
		ErroResponse corpo = new ErroResponse(status.value(), mensagem, LocalDateTime.now());
		return ResponseEntity.status(status).body(corpo);
	}
}
