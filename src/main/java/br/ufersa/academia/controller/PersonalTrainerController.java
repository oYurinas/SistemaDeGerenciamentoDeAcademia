package br.ufersa.academia.controller;

import java.util.List;

import br.ufersa.academia.model.PersonalTrainer;
import br.ufersa.academia.service.PersonalTrainerService;

public class PersonalTrainerController {

    private final PersonalTrainerService personalService;

    public PersonalTrainerController() {
        this.personalService = new PersonalTrainerService();
    }

    public boolean cadastrarPersonal(String nome, String email, String senha, String cref) {
        boolean sucesso = personalService.cadastrarPersonal(nome, email, senha, cref);
        if (sucesso) {
            System.out.println("Personal trainer cadastrado com sucesso!");
        }
        return sucesso;
    }

    public boolean atualizarPersonal(int id, String nome, String email, String senha, String cref) {
        boolean sucesso = personalService.atualizarPersonal(id, nome, email, senha, cref);
        if (sucesso) {
            System.out.println("Personal trainer atualizado com sucesso!");
        }
        return sucesso;
    }

    public boolean desativarPersonal(int id) {
        boolean sucesso = personalService.desativarPersonal(id);
        if (sucesso) {
            System.out.println("Personal trainer desativado com sucesso!");
        }
        return sucesso;
    }

    public List<PersonalTrainer> listarPersonais() {
        return personalService.listarTodos();
    }

    public PersonalTrainer buscarPersonal(int id) {
        return personalService.buscarPorId(id);
    }
}
