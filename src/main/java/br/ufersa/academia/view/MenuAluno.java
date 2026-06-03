package br.ufersa.academia.view;

import java.util.List;
import java.util.Scanner;

import br.ufersa.academia.controller.PagamentoController;
import br.ufersa.academia.controller.TreinoController;
import br.ufersa.academia.model.Aluno;
import br.ufersa.academia.model.Pagamento;
import br.ufersa.academia.model.Plano;
import br.ufersa.academia.model.Treino;

public class MenuAluno {

    private final TreinoController treinoController;
    private final PagamentoController pagamentoController;
    private final Aluno alunoLogado;
    private final Scanner scanner;

    public MenuAluno(Scanner scanner, Aluno alunoLogado) {
        this.pagamentoController = new PagamentoController();
        this.treinoController = new TreinoController(pagamentoController.getService());
        this.alunoLogado = alunoLogado;
        this.scanner = scanner;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n===== MENU ALUNO =====");
            System.out.println("Olá, " + alunoLogado.getNome() + "!");
            System.out.println("1. Visualizar meus treinos");
            System.out.println("2. Visualizar meu plano");
            System.out.println("3. Visualizar meus pagamentos");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> visualizarTreinos();
                case 2 -> visualizarPlano();
                case 3 -> visualizarPagamentos();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void visualizarTreinos() {
        System.out.println("\n--- Meus Treinos ---");
        List<Treino> treinos = treinoController.listarTreinosDoAluno(alunoLogado.getId());
        if (treinos.isEmpty()) {
            System.out.println("Nenhum treino cadastrado.");
            return;
        }
        for (Treino treino : treinos) {
            System.out.printf("[%d] %s | Criado em: %s%n",
                    treino.getId(), treino.getDescricao(), treino.getDataCriacao());
        }
    }

    private void visualizarPlano() {
        System.out.println("\n--- Meu Plano ---");
        Plano plano = alunoLogado.getPlano();
        if (plano == null) {
            System.out.println("Nenhum plano ativo.");
            return;
        }
        System.out.printf("Plano: %s | R$ %.2f | %d meses%n",
                plano.getNome(), plano.getValor(), plano.getDuracaoMeses());
    }

    private void visualizarPagamentos() {
        System.out.println("\n--- Meus Pagamentos ---");
        List<Pagamento> pagamentos = pagamentoController.listarPorAluno(alunoLogado.getId());
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
            return;
        }
        for (Pagamento pagamento : pagamentos) {
            System.out.printf("[%d] R$ %.2f | %s | %s%n",
                    pagamento.getId(), pagamento.getValor(),
                    pagamento.getDataPagamento(), pagamento.getStatus());
        }
    }

    private int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Usando 0.");
            return 0;
        }
    }
}
