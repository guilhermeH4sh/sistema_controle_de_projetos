package com.exemplo.ac2.dto;

public class DadosFuncionarioDTO {

	private Integer id;
	private String nome;
	private String nomeSetor;

	public DadosFuncionarioDTO() {
	}

	public DadosFuncionarioDTO(Integer id, String nome, String nomeSetor) {
		this.id = id;
		this.nome = nome;
		this.nomeSetor = nomeSetor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeSetor() {
		return nomeSetor;
	}

	public void setNomeSetor(String nomeSetor) {
		this.nomeSetor = nomeSetor;
	}
}
