package br.ufersa.academia.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.ufersa.academia.database.ConexaoBanco;
import br.ufersa.academia.model.Treino;

public class TreinoRepository {

    private final Connection conexao;

    public TreinoRepository() {
        this.conexao = ConexaoBanco.getInstancia().getConexao();
    }

    public boolean salvar(Treino treino) {
        String sql = "INSERT INTO treino (descricao, data_criacao) VALUES (?, ?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, treino.getDescricao());
            stmt.setString(2, treino.getDataCriacao().toString());
            stmt.executeUpdate();
            ResultSet chaves = stmt.getGeneratedKeys();
            if (chaves.next()) {
                treino.setId(chaves.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar treino: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Treino treino) {
        String sql = "UPDATE treino SET descricao = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, treino.getDescricao());
            stmt.setInt(2, treino.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar treino: " + e.getMessage());
            return false;
        }
    }

    public Treino buscarPorId(int id) {
        String sql = "SELECT * FROM treino WHERE id = ? AND ativo = 1";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Treino(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        LocalDate.parse(rs.getString("data_criacao"))
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar treino: " + e.getMessage());
        }
        return null;
    }

    public boolean desativar(int id) {
        String sql = "UPDATE treino SET ativo = 0 WHERE id = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao desativar treino: " + e.getMessage());
            return false;
        }
    }

    public boolean vincularAoAluno(int treinoId, int alunoId) {
        String sql = "INSERT OR IGNORE INTO aluno_treino (aluno_id, treino_id) VALUES (?, ?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, alunoId);
            stmt.setInt(2, treinoId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao vincular treino ao aluno: " + e.getMessage());
            return false;
        }
    }

    public List<Treino> listarPorAluno(int alunoId) {
        String sql = """
            SELECT t.* FROM treino t
            JOIN aluno_treino at ON t.id = at.treino_id
            WHERE at.aluno_id = ? AND t.ativo = 1
        """;
        List<Treino> treinos = new ArrayList<>();
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, alunoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                treinos.add(new Treino(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        LocalDate.parse(rs.getString("data_criacao"))
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar treinos do aluno: " + e.getMessage());
        }
        return treinos;
    }

    public List<Treino> listar() {
        List<Treino> treinos = new ArrayList<>();
        String sql = "SELECT * FROM treino WHERE ativo = 1";
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                treinos.add(new Treino(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        LocalDate.parse(rs.getString("data_criacao"))
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar treinos: " + e.getMessage());
        }
        return treinos;
    }
}
