package br.ufersa.academia.model;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario {

    private String matricula;
    private Plano plano;
    private List<Pagamento> pagamentos;
    private List<Treino> treinos;

    public Aluno(int id, String nome, String email, String senha, boolean ativo, String matricula) {
        super(id, nome, email, senha, ativo);
        this.matricula = matricula;
        this.pagamentos = new ArrayList<>();
        this.treinos = new ArrayList<>();
    }

    public Aluno(String nome, String email, String senha, String matricula) {
        super(nome, email, senha);
        this.matricula = matricula;
        this.pagamentos = new ArrayList<>();
        this.treinos = new ArrayList<>();
    }

    public List<Treino> visualizarTreinos() {
        return treinos;
    }

    public Plano visualizarPlano() {
        return plano;
    }

    public List<Pagamento> visualizarPagamentos() {
        return pagamentos;
    }

    public void atualizar(String nome, String email, String senha) {
        if (nome != null && !nome.isBlank()) {
            setNome(nome);
        }
        if (email != null && !email.isBlank()) {
            setEmail(email);
        }
        if (senha != null && !senha.isBlank()) {
            setSenha(senha);
        }
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public List<Treino> getTreinos() {
        return treinos;
    }

    public void setTreinos(List<Treino> treinos) {
        this.treinos = treinos;
    }

    @Override
    public String getTipo() {
        return "ALUNO";
    }
}
