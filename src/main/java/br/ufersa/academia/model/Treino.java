package br.ufersa.academia.model;

import java.time.LocalDate;

public class Treino {

    private int id;
    private String descricao;
    private LocalDate dataCriacao;

    public Treino(int id, String descricao, LocalDate dataCriacao) {
        this.id = id;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
    }

    public Treino(String descricao) {
        this.descricao = descricao;
        this.dataCriacao = LocalDate.now();
    }

    public void atualizar(String novaDescricao) {
        this.descricao = novaDescricao;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
