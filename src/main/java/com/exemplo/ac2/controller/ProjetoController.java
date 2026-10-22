package com.exemplo.ac2.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.ac2.dto.DadosProjetoDTO;
import com.exemplo.ac2.dto.ProjetoDTO;
import com.exemplo.ac2.service.ProjetoService;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

	private final ProjetoService projetoService;

	public ProjetoController(ProjetoService projetoService) {
		this.projetoService = projetoService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void adicionar(@RequestBody ProjetoDTO projeto) {
		projetoService.adicionar(projeto);
	}

	@GetMapping
	public List<DadosProjetoDTO> listar(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
		if (inicio != null || fim != null) {
			return projetoService.buscarPorIntervalo(inicio, fim);
		}
		return projetoService.listar();
	}

	@GetMapping("/{id}")
	public DadosProjetoDTO buscarProjetoPorId(@PathVariable Integer id) {
		return projetoService.buscarProjetoPorId(id);
	}

	@PostMapping("/{idProjeto}/funcionarios/{idFuncionario}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void vincularFuncionario(@PathVariable Integer idProjeto, @PathVariable Integer idFuncionario) {
		projetoService.vincularFuncionario(idProjeto, idFuncionario);
	}
}
