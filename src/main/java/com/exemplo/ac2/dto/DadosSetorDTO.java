package com.exemplo.ac2.dto;

import java.util.ArrayList;
import java.util.List;

public class DadosSetorDTO {

	private Integer id;
	private String nome;
	private List<DadosFuncionarioDTO> funcionarios = new ArrayList<>();

	public DadosSetorDTO() {
	}

	public DadosSetorDTO(Integer id, String nome, List<DadosFuncionarioDTO> funcionarios) {
		this.id = id;
		this.nome = nome;
		this.funcionarios = funcionarios;
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

	public List<DadosFuncionarioDTO> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<DadosFuncionarioDTO> funcionarios) {
		this.funcionarios = funcionarios;
	}
}
