package com.exemplo.ac2.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DadosProjetoDTO {

	private Integer id;
	private String descricao;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private List<DadosFuncionarioDTO> funcionarios = new ArrayList<>();

	public DadosProjetoDTO() {
	}

	public DadosProjetoDTO(Integer id, String descricao, LocalDate dataInicio, LocalDate dataFim,
			List<DadosFuncionarioDTO> funcionarios) {
		this.id = id;
		this.descricao = descricao;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.funcionarios = funcionarios;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public List<DadosFuncionarioDTO> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<DadosFuncionarioDTO> funcionarios) {
		this.funcionarios = funcionarios;
	}
}
