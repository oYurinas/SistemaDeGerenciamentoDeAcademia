package br.ufersa.academia.model;


public class Plano {

    private int id;
    private String nome;
    private double valor;
    private int duracaoMeses;

    public Plano(int id, String nome, double valor, int duracaoMeses) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.duracaoMeses = duracaoMeses;
    }

    public Plano(String nome, double valor, int duracaoMeses) {
        this.nome = nome;
        this.valor = valor;
        this.duracaoMeses = duracaoMeses;
    }

    public void atualizar(String novoNome, double novoValor, int novaDuracao) {
        if (novoNome != null && !novoNome.isBlank()) {
            this.nome = novoNome;
        }
        if (novoValor > 0) {
            this.valor = novoValor;
        }
        if (novaDuracao > 0) {
            this.duracaoMeses = novaDuracao;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getDuracaoMeses() {
        return duracaoMeses;
    }

    public void setDuracaoMeses(int duracaoMeses) {
        this.duracaoMeses = duracaoMeses;
    }
}
