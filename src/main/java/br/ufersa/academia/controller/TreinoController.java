package br.ufersa.academia.controller;

import java.util.List;

import br.ufersa.academia.model.Treino;
import br.ufersa.academia.service.PagamentoService;
import br.ufersa.academia.service.TreinoService;

public class TreinoController {

    private final TreinoService treinoService;

    public TreinoController(PagamentoService pagamentoService) {
        this.treinoService = new TreinoService(pagamentoService);
    }

    public boolean cadastrarTreino(String descricao) {
        boolean sucesso = treinoService.cadastrarTreino(descricao);
        if (sucesso) {
            System.out.println("Treino cadastrado com sucesso!");
        }
        return sucesso;
    }

    public boolean atualizarTreino(int id, String novaDescricao) {
        boolean sucesso = treinoService.atualizarTreino(id, novaDescricao);
        if (sucesso) {
            System.out.println("Treino atualizado com sucesso!");
        }
        return sucesso;
    }

    public boolean desativarTreino(int id) {
        boolean sucesso = treinoService.desativarTreino(id);
        if (sucesso) {
            System.out.println("Treino desativado com sucesso!");
        }
        return sucesso;
    }

    public boolean vincularTreinoAoAluno(int treinoId, int alunoId) {
        boolean sucesso = treinoService.vincularTreinoAoAluno(treinoId, alunoId);
        if (sucesso) {
            System.out.println("Treino vinculado com sucesso!");
        }
        return sucesso;
    }

    public List<Treino> listarTreinos() {
        return treinoService.listarTodos();
    }

    public List<Treino> listarTreinosDoAluno(int alunoId) {
        return treinoService.listarTreinosDoAluno(alunoId);
    }
}
