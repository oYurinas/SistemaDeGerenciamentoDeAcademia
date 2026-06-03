package br.ufersa.academia.controller;

import java.util.List;

import br.ufersa.academia.model.Pagamento;
import br.ufersa.academia.service.PagamentoService;

public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController() {
        this.pagamentoService = new PagamentoService();
    }

    public boolean registrarPagamento(int alunoId, double valor) {
        boolean sucesso = pagamentoService.registrarPagamento(alunoId, valor);
        if (sucesso) {
            System.out.println("Pagamento registrado com sucesso!");
        }
        return sucesso;
    }

    public boolean verificarAdimplencia(int alunoId) {
        return pagamentoService.verificarAdimplencia(alunoId);
    }

    public List<Pagamento> listarPorAluno(int alunoId) {
        return pagamentoService.listarPorAluno(alunoId);
    }

    public List<Pagamento> listarTodos() {
        return pagamentoService.listarTodos();
    }

    public PagamentoService getService() {
        return pagamentoService;
    }
}
