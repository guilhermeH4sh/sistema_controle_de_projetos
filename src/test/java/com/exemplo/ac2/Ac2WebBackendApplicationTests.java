package com.exemplo.ac2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Ac2WebBackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void listaProjetosComFuncionarios() throws Exception {
		mockMvc.perform(get("/projetos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].descricao").value("Portal do cliente"))
				.andExpect(jsonPath("$[0].funcionarios.length()").value(2));
	}

	@Test
	void insereProjeto() throws Exception {
		mockMvc.perform(post("/projetos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"descricao":"Aplicativo interno","dataInicio":"2026-09-01","dataFim":"2026-12-15"}
						"""))
				.andExpect(status().isCreated());
	}

	@Test
	void retornaErroJsonQuandoDataFimEhAnterior() throws Exception {
		mockMvc.perform(post("/projetos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"descricao":"Datas invertidas","dataInicio":"2026-05-10","dataFim":"2026-05-01"}
						"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.mensagem").value("A data de fim não pode ser anterior à data de início."))
				.andExpect(jsonPath("$.status").value(400));
	}

	@Test
	void buscaProjetoPorIdEProjetosDoFuncionario() throws Exception {
		mockMvc.perform(get("/projetos/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.descricao").value("Portal do cliente"))
				.andExpect(jsonPath("$.funcionarios[0].nome").exists());

		mockMvc.perform(get("/funcionarios/1/projetos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].descricao").exists());
	}

	@Test
	void filtraProjetosPeloIntervaloDeInicio() throws Exception {
		mockMvc.perform(get("/projetos").param("inicio", "2026-03-01").param("fim", "2026-05-31"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	void buscaSetorComFuncionarios() throws Exception {
		mockMvc.perform(get("/setores/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome").value("Tecnologia"))
				.andExpect(jsonPath("$.funcionarios.length()").value(2));
	}

	@Test
	void projetoInexistenteRetornaJson() throws Exception {
		mockMvc.perform(get("/projetos/999"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.mensagem").value("Projeto não encontrado."));
	}
}
