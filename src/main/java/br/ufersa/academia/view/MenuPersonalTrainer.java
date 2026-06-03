package br.ufersa.academia.view;

import java.util.List;
import java.util.Scanner;

import br.ufersa.academia.controller.AlunoController;
import br.ufersa.academia.controller.PagamentoController;
import br.ufersa.academia.controller.TreinoController;
import br.ufersa.academia.model.Aluno;
import br.ufersa.academia.model.PersonalTrainer;
import br.ufersa.academia.model.Treino;

public class MenuPersonalTrainer {

    private final TreinoController treinoController;
    private final AlunoController alunoController;
    private final PersonalTrainer personalLogado;
    private final Scanner scanner;

    public MenuPersonalTrainer(Scanner scanner, PersonalTrainer personalLogado) {
        PagamentoController pagamentoController = new PagamentoController();
        this.treinoController = new TreinoController(pagamentoController.getService());
        this.alunoController = new AlunoController();
        this.personalLogado = personalLogado;
        this.scanner = scanner;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n===== MENU PERSONAL TRAINER =====");
            System.out.println("Olá, " + personalLogado.getNome() + "! (CREF: " + personalLogado.getCref() + ")");
            System.out.println("1. Cadastrar treino");
            System.out.println("2. Atualizar treino");
            System.out.println("3. Desativar treino");
            System.out.println("4. Listar treinos");
            System.out.println("5. Vincular treino a aluno");
            System.out.println("6. Listar alunos");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> cadastrarTreino();
                case 2 -> atualizarTreino();
                case 3 -> desativarTreino();
                case 4 -> listarTreinos();
                case 5 -> vincularTreinoAoAluno();
                case 6 -> listarAlunos();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarTreino() {
        System.out.println("\n--- Cadastrar Treino ---");
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        treinoController.cadastrarTreino(descricao);
    }

    private void atualizarTreino() {
        System.out.println("\n--- Atualizar Treino ---");
        System.out.print("ID do treino: ");
        int id = lerInteiro();
        System.out.print("Nova descrição: ");
        String descricao = scanner.nextLine();
        treinoController.atualizarTreino(id, descricao);
    }

    private void desativarTreino() {
        System.out.println("\n--- Desativar Treino ---");
        System.out.print("ID do treino: ");
        int id = lerInteiro();
        treinoController.desativarTreino(id);
    }

    private void listarTreinos() {
        System.out.println("\n--- Lista de Treinos ---");
        List<Treino> treinos = treinoController.listarTreinos();
        if (treinos.isEmpty()) {
            System.out.println("Nenhum treino cadastrado.");
            return;
        }
        for (Treino treino : treinos) {
            System.out.printf("[%d] %s | Criado em: %s%n",
                    treino.getId(), treino.getDescricao(), treino.getDataCriacao());
        }
    }

    private void vincularTreinoAoAluno() {
        System.out.println("\n--- Vincular Treino a Aluno ---");
        System.out.print("ID do treino: ");
        int treinoId = lerInteiro();
        System.out.print("ID do aluno: ");
        int alunoId = lerInteiro();
        treinoController.vincularTreinoAoAluno(treinoId, alunoId);
    }

    private void listarAlunos() {
        System.out.println("\n--- Lista de Alunos ---");
        List<Aluno> alunos = alunoController.listarAlunos();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }
        for (Aluno aluno : alunos) {
            System.out.printf("[%d] %s | %s%n",
                    aluno.getId(), aluno.getNome(), aluno.getMatricula());
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
