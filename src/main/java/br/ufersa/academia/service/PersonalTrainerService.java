package br.ufersa.academia.service;

import java.util.List;

import br.ufersa.academia.model.PersonalTrainer;
import br.ufersa.academia.repository.PersonalTrainerRepository;
import br.ufersa.academia.repository.UsuarioRepository;

public class PersonalTrainerService {

    private final PersonalTrainerRepository personalRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthService authService;

    public PersonalTrainerService() {
        this.personalRepository = new PersonalTrainerRepository();
        this.usuarioRepository = new UsuarioRepository();
        this.authService = new AuthService();
    }

    public boolean cadastrarPersonal(String nome, String email, String senha, String cref) {
        if (nome == null || nome.isBlank()) {
            System.out.println("Nome é obrigatório.");
            return false;
        }
        if (email == null || !email.contains("@")) {
            System.out.println("Email inválido.");
            return false;
        }
        if (senha == null || senha.length() < 4) {
            System.out.println("Senha deve ter ao menos 4 caracteres.");
            return false;
        }
        if (cref == null || cref.isBlank()) {
            System.out.println("CREF é obrigatório.");
            return false;
        }
        String senhaCriptografada = PasswordUtil.hashSenha(senha);
        PersonalTrainer personal = new PersonalTrainer(nome, email, senhaCriptografada, cref);
        return personalRepository.salvar(personal);
    }

    public boolean atualizarPersonal(int id, String nome, String email, String senha, String cref) {
        PersonalTrainer personal = personalRepository.buscarPorId(id);
        if (personal == null) {
            System.out.println("Personal trainer não encontrado.");
            return false;
        }
        if (nome != null && !nome.isBlank()) {
            personal.setNome(nome);
        }
        if (email != null && !email.isBlank()) {
            personal.setEmail(email);
        }
        if (senha != null && !senha.isBlank()) {
            personal.setSenha(PasswordUtil.hashSenha(senha));
        }
        if (cref != null && !cref.isBlank()) {
            personal.setCref(cref);
        }
        return personalRepository.atualizar(personal);
    }

    public boolean desativarPersonal(int id) {
        PersonalTrainer personal = personalRepository.buscarPorId(id);
        if (personal == null) {
            System.out.println("Personal trainer não encontrado ou já desativado.");
            return false;
        }
        return authService.desativarConta(id);
    }

    public PersonalTrainer buscarPorId(int id) {
        return personalRepository.buscarPorId(id);
    }

    public List<PersonalTrainer> listarTodos() {
        return personalRepository.listar();
    }
}
