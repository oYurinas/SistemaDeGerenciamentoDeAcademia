package br.ufersa.academia.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufersa.academia.database.ConexaoBanco;
import br.ufersa.academia.model.Administrador;
import br.ufersa.academia.model.Aluno;
import br.ufersa.academia.model.PersonalTrainer;
import br.ufersa.academia.model.Plano;
import br.ufersa.academia.model.Usuario;
import br.ufersa.academia.service.PasswordUtil;

public class UsuarioRepository {

    private final Connection conexao;

    public UsuarioRepository() {
        this.conexao = ConexaoBanco.getInstancia().getConexao();
    }

    public Usuario buscarPorEmailESenha(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND ativo = 1";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = construirUsuario(rs);
                if (usuario != null && PasswordUtil.verificarSenha(senha, usuario.getSenha())) {
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return construirUsuario(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    private Plano buscarPlanoDoAluno(int alunoId) {
        String sql = """
            SELECT p.* FROM plano p
            JOIN aluno a ON a.plano_id = p.id
            WHERE a.id = ?
        """;
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, alunoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Plano(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("valor"),
                        rs.getInt("duracao_meses")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar plano do aluno: " + e.getMessage());
        }
        return null;
    }

    public boolean desativar(int id) {
        String sql = "UPDATE usuario SET ativo = 0 WHERE id = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao desativar usuário: " + e.getMessage());
            return false;
        }
    }

    private Usuario construirUsuario(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String email = rs.getString("email");
        String senha = rs.getString("senha");
        boolean ativo = rs.getInt("ativo") == 1;
        String tipo = rs.getString("tipo");

        return switch (tipo) {
            case "ADMIN" ->
                new Administrador(id, nome, email, senha, ativo);
            case "PERSONAL" -> {
                String cref = buscarCref(id);
                yield new PersonalTrainer(id, nome, email, senha, ativo, cref);
            }
            case "ALUNO" -> {
                String matricula = buscarMatricula(id);
                Aluno aluno = new Aluno(id, nome, email, senha, ativo, matricula);
                aluno.setPlano(buscarPlanoDoAluno(id));
                yield aluno;
            }
            default ->
                null;
        };
    }

    private String buscarCref(int id) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(
                    "SELECT cref FROM personal_trainer WHERE id = ?"
            );
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("cref");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar CREF: " + e.getMessage());
        }
        return "";
    }

    private String buscarMatricula(int id) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(
                    "SELECT matricula FROM aluno WHERE id = ?"
            );
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("matricula");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar matrícula: " + e.getMessage());
        }
        return "";
    }
}
