package com.algaworks.algafood.log;

import java.util.ArrayList;

public class ConsultaViacao {

    private String operacao;
    private String grupo;
    private String nome;
    private ArrayList<String> rotas = new ArrayList<>();
    private String idTransacao;

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<String> getRotas() {
        return rotas;
    }

    public void setRotas(ArrayList<String> rotas) {
        this.rotas = rotas;
    }

    public String getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(String idTransacao) {
        this.idTransacao = idTransacao;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

}
