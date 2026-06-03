package br.ufersa.academia.model;

public class Administrador extends Usuario {

    public Administrador(int id, String nome, String email, String senha, boolean ativo) {
        super(id, nome, email, senha, ativo);
    }

    public Administrador(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public void cadastrarAluno(Aluno aluno) {
        System.out.println("Aluno " + aluno.getNome() + " cadastrado.");
    }

    public void desativarUsuario(Usuario usuario) {
        usuario.setAtivo(false);
        System.out.println("Usuário " + usuario.getNome() + " desativado.");
    }

    @Override
    public String getTipo() {
        return "ADMIN";
    }
}
