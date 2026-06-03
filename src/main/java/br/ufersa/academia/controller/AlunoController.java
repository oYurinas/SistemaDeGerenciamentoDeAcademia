package br.ufersa.academia.controller;

import java.util.List;

import br.ufersa.academia.model.Aluno;
import br.ufersa.academia.service.AlunoService;

public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController() {
        this.alunoService = new AlunoService();
    }

    public boolean cadastrarAluno(String nome, String email, String senha, String matricula) {
        boolean sucesso = alunoService.cadastrarAluno(nome, email, senha, matricula);
        if (sucesso) {
            System.out.println("Aluno cadastrado com sucesso!");
        }
        return sucesso;
    }

    public boolean atualizarAluno(int id, String nome, String email, String senha) {
        boolean sucesso = alunoService.atualizarAluno(id, nome, email, senha);
        if (sucesso) {
            System.out.println("Aluno atualizado com sucesso!");
        }
        return sucesso;
    }

    public boolean desativarAluno(int id) {
        boolean sucesso = alunoService.desativarAluno(id);
        if (sucesso) {
            System.out.println("Aluno desativado com sucesso!");
        }
        return sucesso;
    }

    public boolean atribuirPlano(int alunoId, int planoId) {
        boolean sucesso = alunoService.atribuirPlano(alunoId, planoId);
        if (sucesso) {
            System.out.println("Plano atribuído com sucesso!");
        }
        return sucesso;
    }

    public List<Aluno> listarAlunos() {
        return alunoService.listarTodos();
    }

    public Aluno buscarAluno(int id) {
        return alunoService.buscarPorId(id);
    }
}
