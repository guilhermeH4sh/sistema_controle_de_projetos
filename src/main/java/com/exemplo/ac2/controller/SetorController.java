package com.exemplo.ac2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.ac2.dto.DadosSetorDTO;
import com.exemplo.ac2.dto.SetorDTO;
import com.exemplo.ac2.service.SetorService;

@RestController
@RequestMapping("/setores")
public class SetorController {

	private final SetorService setorService;

	public SetorController(SetorService setorService) {
		this.setorService = setorService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void adicionar(@RequestBody SetorDTO setor) {
		setorService.adicionar(setor);
	}

	@GetMapping("/{idSetor}")
	public DadosSetorDTO buscarSetorPorId(@PathVariable Integer idSetor) {
		return setorService.buscarSetorPorId(idSetor);
	}
}
