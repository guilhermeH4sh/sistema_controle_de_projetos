package com.exemplo.ac2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exemplo.ac2.model.Setor;

public interface SetorRepository extends JpaRepository<Setor, Integer> {

	boolean existsByNomeIgnoreCase(String nome);

	@Query("""
			SELECT DISTINCT s
			FROM Setor s
			LEFT JOIN FETCH s.funcionarios
			ORDER BY s.nome
			""")
	List<Setor> listarComFuncionarios();
}
