package com.exemplo.ac2.service;

import java.util.Comparator;
import java.util.List;

import com.exemplo.ac2.dto.DadosFuncionarioDTO;
import com.exemplo.ac2.dto.DadosProjetoDTO;
import com.exemplo.ac2.dto.DadosSetorDTO;
import com.exemplo.ac2.model.Funcionario;
import com.exemplo.ac2.model.Projeto;
import com.exemplo.ac2.model.Setor;

public final class Mapeador {

	private Mapeador() {
	}

	public static DadosProjetoDTO paraDadosProjeto(Projeto projeto) {
		List<DadosFuncionarioDTO> funcionarios = projeto.getFuncionarios().stream()
				.sorted(Comparator.comparing(Funcionario::getNome, String.CASE_INSENSITIVE_ORDER))
				.map(Mapeador::paraDadosFuncionario)
				.toList();
		return new DadosProjetoDTO(projeto.getId(), projeto.getDescricao(), projeto.getDataInicio(),
				projeto.getDataFim(), funcionarios);
	}

	public static DadosFuncionarioDTO paraDadosFuncionario(Funcionario funcionario) {
		String nomeSetor = funcionario.getSetor() != null ? funcionario.getSetor().getNome() : null;
		return new DadosFuncionarioDTO(funcionario.getId(), funcionario.getNome(), nomeSetor);
	}

	public static DadosSetorDTO paraDadosSetor(Setor setor) {
		List<DadosFuncionarioDTO> funcionarios = setor.getFuncionarios().stream()
				.sorted(Comparator.comparing(Funcionario::getNome, String.CASE_INSENSITIVE_ORDER))
				.map(funcionario -> new DadosFuncionarioDTO(funcionario.getId(), funcionario.getNome(), setor.getNome()))
				.toList();
		return new DadosSetorDTO(setor.getId(), setor.getNome(), funcionarios);
	}
}
