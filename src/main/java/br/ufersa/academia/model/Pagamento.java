package br.ufersa.academia.model;

import java.time.LocalDate;

public class Pagamento {

    private int id;
    private int alunoId;
    private double valor;
    private LocalDate dataPagamento;
    private String status;

    public Pagamento(int id, int alunoId, double valor, LocalDate dataPagamento, String status) {
        this.id = id;
        this.alunoId = alunoId;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.status = status;
    }

    public Pagamento(int alunoId, double valor) {
        this.alunoId = alunoId;
        this.valor = valor;
        this.dataPagamento = LocalDate.now();
        this.status = "PAGO";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
