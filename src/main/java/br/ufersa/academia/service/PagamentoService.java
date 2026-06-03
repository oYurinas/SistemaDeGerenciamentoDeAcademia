package br.ufersa.academia.service;

import java.util.List;

import br.ufersa.academia.model.Pagamento;
import br.ufersa.academia.repository.PagamentoRepository;

public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService() {
        this.pagamentoRepository = new PagamentoRepository();
    }

    public boolean registrarPagamento(int alunoId, double valor) {
        if (alunoId <= 0) {
            System.out.println("Aluno inválido.");
            return false;
        }
        if (valor <= 0) {
            System.out.println("Valor do pagamento deve ser maior que zero.");
            return false;
        }
        Pagamento pagamento = new Pagamento(alunoId, valor);
        return pagamentoRepository.salvar(pagamento);
    }

    public boolean verificarAdimplencia(int alunoId) {
        return pagamentoRepository.alunoEstaAdimplente(alunoId);
    }

    public List<Pagamento> listarPorAluno(int alunoId) {
        return pagamentoRepository.listarPorAluno(alunoId);
    }

    public List<Pagamento> listarTodos() {
        return pagamentoRepository.listar();
    }
}
