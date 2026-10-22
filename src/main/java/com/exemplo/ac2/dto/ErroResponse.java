package com.exemplo.ac2.dto;

import java.time.LocalDateTime;

public class ErroResponse {

	private int status;
	private String mensagem;
	private LocalDateTime timestamp;

	public ErroResponse() {
	}

	public ErroResponse(int status, String mensagem, LocalDateTime timestamp) {
		this.status = status;
		this.mensagem = mensagem;
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
