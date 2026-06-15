package org.example;


import java.util.ArrayList;
import java.util.List;

public class EquipeRepository {

    private List<Equipe> listaEquipes = new ArrayList<>();
    private int proximoId = 1;

    public Equipe salvar(Equipe equipe) {
        equipe.setId(proximoId++);
        listaEquipes.add(equipe);

        return equipe;
    }

    public List<Equipe> buscarTodas() {
        return listaEquipes;
    }

    public Equipe buscarPorId(int id) {
        for (Equipe e : listaEquipes) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    public Equipe atualizar(int id, Equipe dados) {
        Equipe equipeCadastrada = buscarPorId(id);
        if (equipeCadastrada == null)
            return null;

        equipeCadastrada.setNome(dados.getNome());
        equipeCadastrada.setSigla(dados.getSigla());
        equipeCadastrada.setModalidade(dados.getModalidade());
        equipeCadastrada.setCategoria(dados.getCategoria());
        equipeCadastrada.setTecnico(dados.getTecnico());
        equipeCadastrada.setCapitao(dados.getCapitao());
        equipeCadastrada.setQuantidadeAtletas(dados.getQuantidadeAtletas());
        equipeCadastrada.setDataFundacao(dados.getDataFundacao());
        equipeCadastrada.setStatus(dados.getStatus());
        return equipeCadastrada;
    }

    public boolean remover(int id) {
        return listaEquipes.removeIf(e -> e.getId() == id);
    }

    public void limpar() {
        listaEquipes.clear();
        proximoId = 1;
    }
}
