package br.ufersa.academia.controller;

import java.util.List;

import br.ufersa.academia.model.Plano;
import br.ufersa.academia.service.PlanoService;

public class PlanoController {

    private final PlanoService planoService;

    public PlanoController() {
        this.planoService = new PlanoService();
    }

    public boolean cadastrarPlano(String nome, double valor, int duracaoMeses) {
        boolean sucesso = planoService.cadastrarPlano(nome, valor, duracaoMeses);
        if (sucesso) {
            System.out.println("Plano cadastrado com sucesso!");
        }
        return sucesso;
    }

    public boolean atualizarPlano(int id, String nome, double valor, int duracaoMeses) {
        boolean sucesso = planoService.atualizarPlano(id, nome, valor, duracaoMeses);
        if (sucesso) {
            System.out.println("Plano atualizado com sucesso!");
        }
        return sucesso;
    }

    public boolean desativarPlano(int id) {
        boolean sucesso = planoService.desativarPlano(id);
        if (sucesso) {
            System.out.println("Plano desativado com sucesso!");
        }
        return sucesso;
    }

    public List<Plano> listarPlanos() {
        return planoService.listarTodos();
    }

    public Plano buscarPlano(int id) {
        return planoService.buscarPorId(id);
    }
}
