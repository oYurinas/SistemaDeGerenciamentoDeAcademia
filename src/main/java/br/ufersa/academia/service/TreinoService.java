package br.ufersa.academia.service;

import java.util.List;

import br.ufersa.academia.model.Treino;
import br.ufersa.academia.repository.TreinoRepository;

public class TreinoService {

    private final TreinoRepository treinoRepository;
    private final PagamentoService pagamentoService;

    public TreinoService(PagamentoService pagamentoService) {
        this.treinoRepository = new TreinoRepository();
        this.pagamentoService = pagamentoService;
    }

    public boolean cadastrarTreino(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            System.out.println("Descrição do treino é obrigatória.");
            return false;
        }
        Treino treino = new Treino(descricao);
        return treinoRepository.salvar(treino);
    }

    public boolean atualizarTreino(int id, String novaDescricao) {
        if (novaDescricao == null || novaDescricao.isBlank()) {
            System.out.println("Descrição do treino é obrigatória.");
            return false;
        }
        Treino treino = treinoRepository.buscarPorId(id);
        if (treino == null) {
            System.out.println("Treino não encontrado.");
            return false;
        }
        treino.atualizar(novaDescricao);
        return treinoRepository.atualizar(treino);
    }

    public boolean desativarTreino(int id) {
        boolean sucesso = treinoRepository.desativar(id);
        if (!sucesso) {
            System.out.println("Treino não encontrado ou já desativado.");
        }
        return sucesso;
    }

    public boolean vincularTreinoAoAluno(int treinoId, int alunoId) {
        return treinoRepository.vincularAoAluno(treinoId, alunoId);
    }

    public List<Treino> listarTreinosDoAluno(int alunoId) {
        if (!pagamentoService.verificarAdimplencia(alunoId)) {
            System.out.println("Acesso negado: aluno inadimplente não pode visualizar treinos.");
            return List.of();
        }
        return treinoRepository.listarPorAluno(alunoId);
    }

    public List<Treino> listarTodos() {
        return treinoRepository.listar();
    }
}
