package br.ufersa.academia.service;

import java.util.List;

import br.ufersa.academia.model.Aluno;
import br.ufersa.academia.model.Plano;
import br.ufersa.academia.repository.AlunoRepository;
import br.ufersa.academia.repository.PlanoRepository;
import br.ufersa.academia.repository.UsuarioRepository;

public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final PlanoRepository planoRepository;
    private final UsuarioRepository usuarioRepository;

    public AlunoService() {
        this.alunoRepository = new AlunoRepository();
        this.planoRepository = new PlanoRepository();
        this.usuarioRepository = new UsuarioRepository();
    }

    public boolean cadastrarAluno(String nome, String email, String senha, String matricula) {
        if (nome == null || nome.isBlank()) {
            System.out.println("Nome é obrigatório.");
            return false;
        }
        if (email == null || !email.contains("@")) {
            System.out.println("Email inválido.");
            return false;
        }
        if (senha == null || senha.length() < 4) {
            System.out.println("Senha deve ter ao menos 4 caracteres.");
            return false;
        }
        if (matricula == null || matricula.isBlank()) {
            System.out.println("Matrícula é obrigatória.");
            return false;
        }
        String senhaCriptografada = PasswordUtil.hashSenha(senha);
        Aluno aluno = new Aluno(nome, email, senhaCriptografada, matricula);
        return alunoRepository.salvar(aluno);
    }

    public boolean atualizarAluno(int id, String nome, String email, String senha) {
        Aluno aluno = alunoRepository.buscarPorId(id);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return false;
        }
        String senhaParaAtualizar = senha;
        if (senha != null && !senha.isBlank()) {
            senhaParaAtualizar = PasswordUtil.hashSenha(senha);
        }
        aluno.atualizar(nome, email, senhaParaAtualizar);
        return alunoRepository.atualizar(aluno);
    }

    public boolean atribuirPlano(int alunoId, int planoId) {
        Aluno aluno = alunoRepository.buscarPorId(alunoId);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return false;
        }
        Plano plano = planoRepository.buscarPorId(planoId);
        if (plano == null) {
            System.out.println("Plano não encontrado.");
            return false;
        }
        return alunoRepository.atualizarPlano(alunoId, planoId);
    }

    public boolean desativarAluno(int id) {
        Aluno aluno = alunoRepository.buscarPorId(id);
        if (aluno == null) {
            System.out.println("Aluno não encontrado ou já desativado.");
            return false;
        }
        return usuarioRepository.desativar(id);
    }

    public Aluno buscarPorId(int id) {
        return alunoRepository.buscarPorId(id);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.listar();
    }
}
