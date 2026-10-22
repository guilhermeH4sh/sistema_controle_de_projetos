package com.exemplo.ac2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exemplo.ac2.model.Funcionario;
import com.exemplo.ac2.model.Projeto;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

	@Query("""
			SELECT DISTINCT p
			FROM Projeto p
			JOIN p.funcionarios f
			WHERE f.id = :idFuncionario
			ORDER BY p.dataInicio, p.id
			""")
	List<Projeto> buscarProjetosPorFuncionario(@Param("idFuncionario") Integer idFuncionario);
}
