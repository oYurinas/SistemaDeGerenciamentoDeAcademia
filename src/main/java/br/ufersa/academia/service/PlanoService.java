package br.ufersa.academia.service;

import java.util.List;

import br.ufersa.academia.model.Plano;
import br.ufersa.academia.repository.PlanoRepository;

public class PlanoService {

    private final PlanoRepository planoRepository;

    public PlanoService() {
        this.planoRepository = new PlanoRepository();
    }

    public boolean cadastrarPlano(String nome, double valor, int duracaoMeses) {
        if (nome == null || nome.isBlank()) {
            System.out.println("Nome do plano é obrigatório.");
            return false;
        }
        if (valor <= 0) {
            System.out.println("Valor do plano deve ser maior que zero.");
            return false;
        }
        if (duracaoMeses <= 0) {
            System.out.println("Duração do plano deve ser maior que zero.");
            return false;
        }
        Plano plano = new Plano(nome, valor, duracaoMeses);
        return planoRepository.salvar(plano);
    }

    public boolean atualizarPlano(int id, String nome, double valor, int duracaoMeses) {
        Plano plano = planoRepository.buscarPorId(id);
        if (plano == null) {
            System.out.println("Plano não encontrado.");
            return false;
        }
        plano.atualizar(nome, valor, duracaoMeses);
        return planoRepository.atualizar(plano);
    }

    public boolean desativarPlano(int id) {
        Plano plano = planoRepository.buscarPorId(id);
        if (plano == null) {
            System.out.println("Plano não encontrado ou já desativado.");
            return false;
        }
        return planoRepository.desativar(id);
    }

    public Plano buscarPorId(int id) {
        return planoRepository.buscarPorId(id);
    }

    public List<Plano> listarTodos() {
        return planoRepository.listar();
    }
}
