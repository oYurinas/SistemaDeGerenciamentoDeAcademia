package br.ufersa.academia.view;

import java.util.Scanner;

import br.ufersa.academia.model.Administrador;
import br.ufersa.academia.model.Aluno;
import br.ufersa.academia.model.PersonalTrainer;
import br.ufersa.academia.model.Usuario;
import br.ufersa.academia.service.AuthService;

public class MenuPrincipal {

    private final AuthService authService;
    private final Scanner scanner;

    public MenuPrincipal() {
        this.authService = new AuthService();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("====================================");
        System.out.println("  SISTEMA DE GERENCIAMENTO ACADEMIA ");
        System.out.println("====================================");

        int opcao;
        do {
            System.out.println("\n1. Fazer login");
            System.out.println("0. Sair do sistema");
            System.out.print("Escolha: ");
            opcao = lerInteiro();

            switch (opcao) {
                case 1 ->
                    realizarLogin();
                case 0 ->
                    System.out.println("Sistema encerrado.");
                default ->
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void realizarLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = authService.realizarLogin(email, senha);
        if (usuario == null) {
            return;
        }

        System.out.println("Bem-vindo(a), " + usuario.getNome() + "!");

        if (usuario instanceof Administrador) {
            new MenuAdministrador(scanner).exibir();
        } else if (usuario instanceof PersonalTrainer personalTrainer) {
            new MenuPersonalTrainer(scanner, personalTrainer).exibir();
        } else if (usuario instanceof Aluno aluno) {
            new MenuAluno(scanner, aluno).exibir();
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
