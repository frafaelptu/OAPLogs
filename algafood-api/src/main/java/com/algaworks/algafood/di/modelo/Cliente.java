package com.algaworks.algafood.di.modelo;

import com.algaworks.algafood.config.oap.LogContext;

public class Cliente {

	private String nome;
	private String email;
	private String telefone;
	private boolean ativo = false;

	public Cliente(String nome, String email, String telefone) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getTelefone() {
		return telefone;
	}

	public boolean isAtivo() {
		return ativo;
	}
	
	public void ativar() throws Exception {
		LogContext.put("antes_lancar_excecao_ativacao_clienet", "true");

//		if (1==1){
//			throw new Exception("Erro ao tentar ativar notificação");
//		}

		this.ativo = true;
		LogContext.put("notificacao_ativada", "true");
	}

}
