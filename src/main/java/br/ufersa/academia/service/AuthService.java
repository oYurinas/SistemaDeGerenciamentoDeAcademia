package br.ufersa.academia.service;

import br.ufersa.academia.model.Usuario;
import br.ufersa.academia.repository.UsuarioRepository;

public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public AuthService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public Usuario realizarLogin(String email, String senha) {
        if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
            System.out.println("Email e senha são obrigatórios.");
            return null;
        }
        Usuario usuario = usuarioRepository.buscarPorEmailESenha(email, senha);
        if (usuario == null) {
            System.out.println("Email ou senha inválidos, ou conta desativada.");
        }
        return usuario;
    }

    public boolean desativarConta(int id) {
        Usuario usuario = usuarioRepository.buscarPorId(id);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return false;
        }
        if (!usuario.isAtivo()) {
            System.out.println("Conta já está desativada.");
            return false;
        }
        return usuarioRepository.desativar(id);
    }
}
