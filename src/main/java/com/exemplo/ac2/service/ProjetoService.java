package com.exemplo.ac2.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exemplo.ac2.dto.DadosProjetoDTO;
import com.exemplo.ac2.dto.ProjetoDTO;
import com.exemplo.ac2.exception.RecursoNaoEncontradoException;
import com.exemplo.ac2.exception.RegraNegocioException;
import com.exemplo.ac2.model.Funcionario;
import com.exemplo.ac2.model.Projeto;
import com.exemplo.ac2.repository.FuncionarioRepository;
import com.exemplo.ac2.repository.ProjetoRepository;

@Service
public class ProjetoService {

	private final ProjetoRepository projetoRepository;
	private final FuncionarioRepository funcionarioRepository;

	public ProjetoService(ProjetoRepository projetoRepository, FuncionarioRepository funcionarioRepository) {
		this.projetoRepository = projetoRepository;
		this.funcionarioRepository = funcionarioRepository;
	}

	@Transactional
	public void adicionar(ProjetoDTO dto) {
		validar(dto);
		Projeto projeto = new Projeto(dto.getDescricao().trim(), dto.getDataInicio(), dto.getDataFim());
		projetoRepository.save(projeto);
	}

	@Transactional(readOnly = true)
	public DadosProjetoDTO buscarProjetoPorId(Integer id) {
		List<Projeto> encontrados = projetoRepository.buscarPorIdComFuncionarios(id);
		if (encontrados.isEmpty()) {
			throw new RecursoNaoEncontradoException("Projeto não encontrado.");
		}
		return Mapeador.paraDadosProjeto(encontrados.get(0));
	}

	@Transactional(readOnly = true)
	public List<DadosProjetoDTO> listar() {
		return projetoRepository.listarComFuncionarios().stream()
				.map(Mapeador::paraDadosProjeto)
				.sorted(Comparator.comparing(DadosProjetoDTO::getDataInicio).thenComparing(DadosProjetoDTO::getId))
				.toList();
	}

	@Transactional(readOnly = true)
	public List<DadosProjetoDTO> buscarPorIntervalo(LocalDate inicio, LocalDate fim) {
		if (inicio == null || fim == null) {
			throw new RegraNegocioException("Informe a data inicial e a data final do intervalo.");
		}
		if (inicio.isAfter(fim)) {
			throw new RegraNegocioException("A data inicial do intervalo não pode ser posterior à data final.");
		}
		return projetoRepository.buscarPorDataInicioEntre(inicio, fim).stream()
				.map(Mapeador::paraDadosProjeto)
				.toList();
	}

	@Transactional
	public void vincularFuncionario(Integer idProjeto, Integer idFuncionario) {
		Projeto projeto = projetoRepository.findById(idProjeto)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado."));
		Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado."));

		boolean jaVinculado = projeto.getFuncionarios().stream()
				.anyMatch(item -> item.getId().equals(idFuncionario));
		if (jaVinculado) {
			throw new RegraNegocioException("Funcionário já está vinculado a este projeto.");
		}

		projeto.getFuncionarios().add(funcionario);
	}

	private void validar(ProjetoDTO dto) {
		if (dto == null) {
			throw new RegraNegocioException("Os dados do projeto são obrigatórios.");
		}
		if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
			throw new RegraNegocioException("A descrição do projeto é obrigatória.");
		}
		if (dto.getDescricao().trim().length() > 200) {
			throw new RegraNegocioException("A descrição do projeto deve ter no máximo 200 caracteres.");
		}
		if (dto.getDataInicio() == null) {
			throw new RegraNegocioException("A data de início é obrigatória.");
		}
		if (dto.getDataFim() == null) {
			throw new RegraNegocioException("A data de fim é obrigatória.");
		}
		if (dto.getDataFim().isBefore(dto.getDataInicio())) {
			throw new RegraNegocioException("A data de fim não pode ser anterior à data de início.");
		}
	}
}
