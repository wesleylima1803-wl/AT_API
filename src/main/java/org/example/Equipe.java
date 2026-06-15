package org.example;

import java.time.LocalDate;

public class Equipe {
    private int id;
    private String nome;
    private String sigla;
    private String modalidade;
    private String categoria;
    private String tecnico;
    private String capitao;
    private int quantidadeAtletas;
    private LocalDate dataFundacao;
    private StatusEquipe status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getCapitao() {
        return capitao;
    }

    public void setCapitao(String capitao) {
        this.capitao = capitao;
    }

    public int getQuantidadeAtletas() {
        return quantidadeAtletas;
    }

    public void setQuantidadeAtletas(int quantidadeAtletas) {
        this.quantidadeAtletas = quantidadeAtletas;
    }

    public LocalDate getDataFundacao() {
        return dataFundacao;
    }

    public void setDataFundacao(LocalDate dataFundacao) {
        this.dataFundacao = dataFundacao;
    }

    public StatusEquipe getStatus() {
        return status;
    }

    public void setStatus(StatusEquipe status) {
        this.status = status;
    }
}
