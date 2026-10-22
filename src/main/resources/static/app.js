const formProjeto = document.querySelector("#form-projeto");
const formFiltro = document.querySelector("#form-filtro");
const lista = document.querySelector("#lista");
const resumo = document.querySelector("#resumo");
const alerta = document.querySelector("#alerta");
const botaoSalvar = document.querySelector("#botao-salvar");

formProjeto.addEventListener("submit", salvarProjeto);
formFiltro.addEventListener("submit", aplicarFiltro);
document.querySelector("#limpar-filtro").addEventListener("click", limparFiltro);

carregarProjetos();

async function salvarProjeto(evento) {
	evento.preventDefault();
	const corpo = {
		descricao: document.querySelector("#descricao").value.trim(),
		dataInicio: document.querySelector("#dataInicio").value,
		dataFim: document.querySelector("#dataFim").value
	};

	botaoSalvar.disabled = true;
	try {
		const resposta = await fetch("/projetos", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(corpo)
		});
		if (!resposta.ok) {
			mostrarAlerta(await lerMensagem(resposta), false);
			return;
		}
		formProjeto.reset();
		mostrarAlerta("Projeto cadastrado.", true);
		await carregarProjetos();
	} catch (erro) {
		mostrarAlerta("Não foi possível falar com o servidor.", false);
	} finally {
		botaoSalvar.disabled = false;
	}
}

async function aplicarFiltro(evento) {
	evento.preventDefault();
	const inicio = document.querySelector("#filtroInicio").value;
	const fim = document.querySelector("#filtroFim").value;
	if ((inicio && !fim) || (!inicio && fim)) {
		mostrarAlerta("Informe a data inicial e a data final do intervalo.", false);
		return;
	}
	await carregarProjetos(inicio, fim);
}

function limparFiltro() {
	formFiltro.reset();
	carregarProjetos();
}

async function carregarProjetos(inicio, fim) {
	const params = new URLSearchParams();
	if (inicio && fim) {
		params.set("inicio", inicio);
		params.set("fim", fim);
	}
	const url = params.size ? `/projetos?${params}` : "/projetos";

	try {
		const resposta = await fetch(url);
		if (!resposta.ok) {
			mostrarAlerta(await lerMensagem(resposta), false);
			renderizar([]);
			return;
		}
		const projetos = await resposta.json();
		renderizar(projetos, Boolean(inicio && fim));
	} catch (erro) {
		mostrarAlerta("Não foi possível carregar os projetos.", false);
		renderizar([]);
	}
}

function renderizar(projetos, filtrado) {
	const total = projetos.length;
	const texto = total === 1 ? "1 projeto" : `${total} projetos`;
	resumo.textContent = filtrado ? `${texto} com início no intervalo.` : `${texto} cadastrados.`;

	if (total === 0) {
		lista.innerHTML = '<p class="vazio">Nenhum projeto encontrado.</p>';
		return;
	}

	lista.innerHTML = projetos.map((projeto) => {
		const pessoas = projeto.funcionarios.length
			? projeto.funcionarios.map((funcionario) =>
				`<span class="pessoa">${escapar(funcionario.nome)} · ${escapar(funcionario.nomeSetor || "Sem setor")}</span>`
			).join("")
			: '<span class="sem-equipe">Nenhum funcionário vinculado</span>';

		return `
			<article class="cartao">
				<p class="meta">Projeto ${projeto.id}</p>
				<h3>${escapar(projeto.descricao)}</h3>
				<p class="periodo">${formatarData(projeto.dataInicio)} até ${formatarData(projeto.dataFim)}</p>
				<div class="pessoas">${pessoas}</div>
			</article>
		`;
	}).join("");
}

async function lerMensagem(resposta) {
	try {
		const corpo = await resposta.json();
		return corpo.mensagem || "Não foi possível concluir a operação.";
	} catch (erro) {
		return "Não foi possível concluir a operação.";
	}
}

function mostrarAlerta(mensagem, sucesso) {
	alerta.hidden = false;
	alerta.textContent = mensagem;
	alerta.classList.toggle("ok", sucesso);
}

function formatarData(iso) {
	if (!iso) {
		return "";
	}
	const [ano, mes, dia] = iso.split("-");
	return `${dia}/${mes}/${ano}`;
}

function escapar(texto) {
	return String(texto)
		.replaceAll("&", "&amp;")
		.replaceAll("<", "&lt;")
		.replaceAll(">", "&gt;")
		.replaceAll('"', "&quot;");
}
