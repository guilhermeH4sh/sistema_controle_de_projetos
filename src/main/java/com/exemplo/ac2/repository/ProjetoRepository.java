package com.exemplo.ac2.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exemplo.ac2.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

	@Query("""
			SELECT DISTINCT p
			FROM Projeto p
			LEFT JOIN FETCH p.funcionarios f
			LEFT JOIN FETCH f.setor
			WHERE p.id = :id
			""")
	List<Projeto> buscarPorIdComFuncionarios(@Param("id") Integer id);

	@Query("""
			SELECT p
			FROM Projeto p
			WHERE p.dataInicio BETWEEN :inicio AND :fim
			ORDER BY p.dataInicio, p.id
			""")
	List<Projeto> buscarPorDataInicioEntre(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

	@Query("""
			SELECT DISTINCT p
			FROM Projeto p
			LEFT JOIN FETCH p.funcionarios f
			LEFT JOIN FETCH f.setor
			ORDER BY p.dataInicio, p.id
			""")
	List<Projeto> listarComFuncionarios();
}
