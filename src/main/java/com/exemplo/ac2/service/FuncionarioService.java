package com.exemplo.ac2.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exemplo.ac2.dto.DadosProjetoDTO;
import com.exemplo.ac2.dto.FuncionarioDTO;
import com.exemplo.ac2.exception.RecursoNaoEncontradoException;
import com.exemplo.ac2.exception.RegraNegocioException;
import com.exemplo.ac2.model.Funcionario;
import com.exemplo.ac2.model.Setor;
import com.exemplo.ac2.repository.FuncionarioRepository;
import com.exemplo.ac2.repository.SetorRepository;

@Service
public class FuncionarioService {

	private final FuncionarioRepository funcionarioRepository;
	private final SetorRepository setorRepository;

	public FuncionarioService(FuncionarioRepository funcionarioRepository, SetorRepository setorRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.setorRepository = setorRepository;
	}

	@Transactional
	public void adicionar(FuncionarioDTO dto) {
		if (dto == null || dto.getNome() == null || dto.getNome().isBlank()) {
			throw new RegraNegocioException("O nome do funcionário é obrigatório.");
		}
		if (dto.getNome().trim().length() > 120) {
			throw new RegraNegocioException("O nome do funcionário deve ter no máximo 120 caracteres.");
		}
		if (dto.getIdSetor() == null) {
			throw new RegraNegocioException("O funcionário deve estar vinculado a um setor.");
		}

		Setor setor = setorRepository.findById(dto.getIdSetor())
				.orElseThrow(() -> new RecursoNaoEncontradoException("Setor não encontrado."));

		Funcionario funcionario = new Funcionario(dto.getNome().trim());
		funcionario.setSetor(setor);
		funcionarioRepository.save(funcionario);
	}

	@Transactional(readOnly = true)
	public List<DadosProjetoDTO> buscarProjetos(Integer idFuncionario) {
		if (!funcionarioRepository.existsById(idFuncionario)) {
			throw new RecursoNaoEncontradoException("Funcionário não encontrado.");
		}
		return funcionarioRepository.buscarProjetosPorFuncionario(idFuncionario).stream()
				.map(Mapeador::paraDadosProjeto)
				.toList();
	}
}
