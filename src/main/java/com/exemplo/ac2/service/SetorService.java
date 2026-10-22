package com.exemplo.ac2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exemplo.ac2.dto.DadosSetorDTO;
import com.exemplo.ac2.dto.SetorDTO;
import com.exemplo.ac2.exception.RecursoNaoEncontradoException;
import com.exemplo.ac2.exception.RegraNegocioException;
import com.exemplo.ac2.model.Setor;
import com.exemplo.ac2.repository.SetorRepository;

@Service
public class SetorService {

	private final SetorRepository setorRepository;

	public SetorService(SetorRepository setorRepository) {
		this.setorRepository = setorRepository;
	}

	@Transactional
	public void adicionar(SetorDTO dto) {
		if (dto == null || dto.getNome() == null || dto.getNome().isBlank()) {
			throw new RegraNegocioException("O nome do setor é obrigatório.");
		}
		String nome = dto.getNome().trim();
		if (nome.length() > 100) {
			throw new RegraNegocioException("O nome do setor deve ter no máximo 100 caracteres.");
		}
		if (setorRepository.existsByNomeIgnoreCase(nome)) {
			throw new RegraNegocioException("Já existe um setor com esse nome.");
		}
		setorRepository.save(new Setor(nome));
	}

	@Transactional(readOnly = true)
	public DadosSetorDTO buscarSetorPorId(Integer idSetor) {
		return setorRepository.listarComFuncionarios().stream()
				.filter(setor -> setor.getId().equals(idSetor))
				.findFirst()
				.map(Mapeador::paraDadosSetor)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Setor não encontrado."));
	}
}
