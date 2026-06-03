package br.ufersa.academia.view;

import java.util.List;
import java.util.Scanner;

import br.ufersa.academia.controller.AlunoController;
import br.ufersa.academia.controller.PagamentoController;
import br.ufersa.academia.controller.PersonalTrainerController;
import br.ufersa.academia.controller.PlanoController;
import br.ufersa.academia.model.Aluno;
import br.ufersa.academia.model.Pagamento;
import br.ufersa.academia.model.PersonalTrainer;
import br.ufersa.academia.model.Plano;

public class MenuAdministrador {

    private final AlunoController alunoController;
    private final PersonalTrainerController personalController;
    private final PlanoController planoController;
    private final PagamentoController pagamentoController;
    private final Scanner scanner;

    public MenuAdministrador(Scanner scanner) {
        this.alunoController = new AlunoController();
        this.personalController = new PersonalTrainerController();
        this.planoController = new PlanoController();
        this.pagamentoController = new PagamentoController();
        this.scanner = scanner;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n===== MENU ADMINISTRADOR =====");
            System.out.println("--- Alunos ---");
            System.out.println("1. Cadastrar aluno");
            System.out.println("2. Atualizar aluno");
            System.out.println("3. Desativar aluno");
            System.out.println("4. Listar alunos");
            System.out.println("5. Atribuir plano ao aluno");
            System.out.println("--- Personal Trainers ---");
            System.out.println("6. Cadastrar personal trainer");
            System.out.println("7. Atualizar personal trainer");
            System.out.println("8. Desativar personal trainer");
            System.out.println("9. Listar personal trainers");
            System.out.println("--- Planos ---");
            System.out.println("10. Cadastrar plano");
            System.out.println("11. Atualizar plano");
            System.out.println("12. Desativar plano");
            System.out.println("13. Listar planos");
            System.out.println("--- Pagamentos ---");
            System.out.println("14. Registrar pagamento");
            System.out.println("15. Listar pagamentos");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = lerInteiro();

            switch (opcao) {
                case 1 ->
                    cadastrarAluno();
                case 2 ->
                    atualizarAluno();
                case 3 ->
                    desativarAluno();
                case 4 ->
                    listarAlunos();
                case 5 ->
                    atribuirPlano();
                case 6 ->
                    cadastrarPersonal();
                case 7 ->
                    atualizarPersonal();
                case 8 ->
                    desativarPersonal();
                case 9 ->
                    listarPersonais();
                case 10 ->
                    cadastrarPlano();
                case 11 ->
                    atualizarPlano();
                case 12 ->
                    desativarPlano();
                case 13 ->
                    listarPlanos();
                case 14 ->
                    registrarPagamento();
                case 15 ->
                    listarPagamentos();
                case 0 ->
                    System.out.println("Saindo...");
                default ->
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarAluno() {
        System.out.println("\n--- Cadastrar Aluno ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        alunoController.cadastrarAluno(nome, email, senha, matricula);
    }

    private void atualizarAluno() {
        System.out.println("\n--- Atualizar Aluno ---");
        System.out.print("ID do aluno: ");
        int id = lerInteiro();
        System.out.print("Novo nome (Enter para manter): ");
        String nome = scanner.nextLine();
        System.out.print("Novo email (Enter para manter): ");
        String email = scanner.nextLine();
        System.out.print("Nova senha (Enter para manter): ");
        String senha = scanner.nextLine();
        alunoController.atualizarAluno(id, nome, email, senha);
    }

    private void desativarAluno() {
        System.out.println("\n--- Desativar Aluno ---");
        System.out.print("ID do aluno: ");
        int id = lerInteiro();
        alunoController.desativarAluno(id);
    }

    private void listarAlunos() {
        System.out.println("\n--- Lista de Alunos ---");
        List<Aluno> alunos = alunoController.listarAlunos();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }
        for (Aluno aluno : alunos) {
            String planoNome = aluno.getPlano() != null ? aluno.getPlano().getNome() : "Sem plano";
            String status = aluno.isAtivo() ? "Ativo" : "Inativo";
            System.out.printf("[%d] %s | %s | Plano: %s | Status: %s%n",
                    aluno.getId(), aluno.getNome(), aluno.getMatricula(), planoNome, status);
        }
    }

    private void atribuirPlano() {
        System.out.println("\n--- Atribuir Plano ---");
        System.out.print("ID do aluno: ");
        int alunoId = lerInteiro();
        System.out.print("ID do plano: ");
        int planoId = lerInteiro();
        alunoController.atribuirPlano(alunoId, planoId);
    }

    private void cadastrarPersonal() {
        System.out.println("\n--- Cadastrar Personal Trainer ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("CREF: ");
        String cref = scanner.nextLine();
        personalController.cadastrarPersonal(nome, email, senha, cref);
    }

    private void atualizarPersonal() {
        System.out.println("\n--- Atualizar Personal Trainer ---");
        System.out.print("ID do personal trainer: ");
        int id = lerInteiro();
        System.out.print("Novo nome (Enter para manter): ");
        String nome = scanner.nextLine();
        System.out.print("Novo email (Enter para manter): ");
        String email = scanner.nextLine();
        System.out.print("Nova senha (Enter para manter): ");
        String senha = scanner.nextLine();
        System.out.print("Novo CREF (Enter para manter): ");
        String cref = scanner.nextLine();
        personalController.atualizarPersonal(id, nome, email, senha, cref);
    }

    private void desativarPersonal() {
        System.out.println("\n--- Desativar Personal Trainer ---");
        System.out.print("ID do personal trainer: ");
        int id = lerInteiro();
        personalController.desativarPersonal(id);
    }

    private void listarPersonais() {
        System.out.println("\n--- Lista de Personal Trainers ---");
        List<PersonalTrainer> personais = personalController.listarPersonais();
        if (personais.isEmpty()) {
            System.out.println("Nenhum personal trainer cadastrado.");
            return;
        }
        for (PersonalTrainer pt : personais) {
            String status = pt.isAtivo() ? "Ativo" : "Inativo";
            System.out.printf("[%d] %s | CREF: %s | %s | Status: %s%n",
                    pt.getId(), pt.getNome(), pt.getCref(), pt.getEmail(), status);
        }
    }

    private void cadastrarPlano() {
        System.out.println("\n--- Cadastrar Plano ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Valor (R$): ");
        double valor = lerDouble();
        System.out.print("Duração (meses): ");
        int meses = lerInteiro();
        planoController.cadastrarPlano(nome, valor, meses);
    }

    private void atualizarPlano() {
        System.out.println("\n--- Atualizar Plano ---");
        System.out.print("ID do plano: ");
        int id = lerInteiro();
        System.out.print("Novo nome (Enter para manter): ");
        String nome = scanner.nextLine();
        System.out.print("Novo valor (0 para manter): ");
        double valor = lerDouble();
        System.out.print("Nova duração em meses (0 para manter): ");
        int meses = lerInteiro();
        planoController.atualizarPlano(id, nome, valor, meses);
    }

    private void desativarPlano() {
        System.out.println("\n--- Desativar Plano ---");
        System.out.print("ID do plano: ");
        int id = lerInteiro();
        planoController.desativarPlano(id);
    }

    private void listarPlanos() {
        System.out.println("\n--- Lista de Planos ---");
        List<Plano> planos = planoController.listarPlanos();
        if (planos.isEmpty()) {
            System.out.println("Nenhum plano cadastrado.");
            return;
        }
        for (Plano plano : planos) {
            System.out.printf("[%d] %s | R$ %.2f | %d meses%n",
                    plano.getId(), plano.getNome(), plano.getValor(), plano.getDuracaoMeses());
        }
    }

    private void registrarPagamento() {
        System.out.println("\n--- Registrar Pagamento ---");
        System.out.print("ID do aluno: ");
        int alunoId = lerInteiro();
        System.out.print("Valor (R$): ");
        double valor = lerDouble();
        pagamentoController.registrarPagamento(alunoId, valor);
    }

    private void listarPagamentos() {
        System.out.println("\n--- Lista de Pagamentos ---");
        List<Pagamento> pagamentos = pagamentoController.listarTodos();
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
            return;
        }
        for (Pagamento pagamento : pagamentos) {
            System.out.printf("[%d] Aluno ID: %d | R$ %.2f | %s | %s%n",
                    pagamento.getId(), pagamento.getAlunoId(), pagamento.getValor(),
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

    private double lerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Usando 0.");
            return 0;
        }
    }
}
