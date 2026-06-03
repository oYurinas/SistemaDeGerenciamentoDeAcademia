package br.ufersa.academia.model;

public class PersonalTrainer extends Usuario {

    private String cref;

    public PersonalTrainer(int id, String nome, String email, String senha, boolean ativo, String cref) {
        super(id, nome, email, senha, ativo);
        this.cref = cref;
    }

    public PersonalTrainer(String nome, String email, String senha, String cref) {
        super(nome, email, senha);
        this.cref = cref;
    }

    public void cadastrarTreino(Treino treino) {
        System.out.println("Treino cadastrado: " + treino.getDescricao());
    }

    public void atualizarTreino(Treino treino, String novaDescricao) {
        treino.setDescricao(novaDescricao);
        System.out.println("Treino atualizado.");
    }

    public String getCref() {
        return cref;
    }

    public void setCref(String cref) {
        this.cref = cref;
    }

    @Override
    public String getTipo() {
        return "PERSONAL";
    }
}
