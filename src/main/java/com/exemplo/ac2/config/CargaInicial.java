package com.exemplo.ac2.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.exemplo.ac2.model.Funcionario;
import com.exemplo.ac2.model.Projeto;
import com.exemplo.ac2.model.Setor;
import com.exemplo.ac2.repository.FuncionarioRepository;
import com.exemplo.ac2.repository.ProjetoRepository;
import com.exemplo.ac2.repository.SetorRepository;

@Component
public class CargaInicial implements CommandLineRunner {

	private final SetorRepository setorRepository;
	private final FuncionarioRepository funcionarioRepository;
	private final ProjetoRepository projetoRepository;

	public CargaInicial(SetorRepository setorRepository, FuncionarioRepository funcionarioRepository,
			ProjetoRepository projetoRepository) {
		this.setorRepository = setorRepository;
		this.funcionarioRepository = funcionarioRepository;
		this.projetoRepository = projetoRepository;
	}

	@Override
	@Transactional
	public void run(String... args) {
		if (projetoRepository.count() > 0) {
			return;
		}

		Setor tecnologia = setorRepository.save(new Setor("Tecnologia"));
		Setor recursosHumanos = setorRepository.save(new Setor("Recursos Humanos"));

		Funcionario ana = funcionarioRepository.save(criar("Ana Souza", tecnologia));
		Funcionario bruno = funcionarioRepository.save(criar("Bruno Lima", tecnologia));
		Funcionario carla = funcionarioRepository.save(criar("Carla Mendes", recursosHumanos));

		Projeto portal = new Projeto("Portal do cliente", LocalDate.of(2026, 1, 10), LocalDate.of(2026, 6, 30));
		portal.getFuncionarios().add(ana);
		portal.getFuncionarios().add(bruno);

		Projeto migracao = new Projeto("Migração do banco de dados", LocalDate.of(2026, 3, 1),
				LocalDate.of(2026, 4, 15));
		migracao.getFuncionarios().add(ana);

		Projeto integracao = new Projeto("Integração de ponto eletrônico", LocalDate.of(2026, 5, 4),
				LocalDate.of(2026, 8, 20));
		integracao.getFuncionarios().add(carla);
		integracao.getFuncionarios().add(bruno);

		projetoRepository.save(portal);
		projetoRepository.save(migracao);
		projetoRepository.save(integracao);
	}

	private Funcionario criar(String nome, Setor setor) {
		Funcionario funcionario = new Funcionario(nome);
		funcionario.setSetor(setor);
		return funcionario;
	}
}
