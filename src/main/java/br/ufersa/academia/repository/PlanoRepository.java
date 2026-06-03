package br.ufersa.academia.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufersa.academia.database.ConexaoBanco;
import br.ufersa.academia.model.Plano;

public class PlanoRepository {

    private final Connection conexao;

    public PlanoRepository() {
        this.conexao = ConexaoBanco.getInstancia().getConexao();
    }

    public boolean salvar(Plano plano) {
        String sql = "INSERT INTO plano (nome, valor, duracao_meses) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, plano.getNome());
            stmt.setDouble(2, plano.getValor());
            stmt.setInt(3, plano.getDuracaoMeses());
            stmt.executeUpdate();
            ResultSet chaves = stmt.getGeneratedKeys();
            if (chaves.next()) {
                plano.setId(chaves.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar plano: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Plano plano) {
        String sql = "UPDATE plano SET nome = ?, valor = ?, duracao_meses = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, plano.getNome());
            stmt.setDouble(2, plano.getValor());
            stmt.setInt(3, plano.getDuracaoMeses());
            stmt.setInt(4, plano.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar plano: " + e.getMessage());
            return false;
        }
    }

    public boolean desativar(int id) {
        String sql = "UPDATE plano SET ativo = 0 WHERE id = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao desativar plano: " + e.getMessage());
            return false;
        }
    }

    public Plano buscarPorId(int id) {
        String sql = "SELECT * FROM plano WHERE id = ? AND ativo = 1";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
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
            System.out.println("Erro ao buscar plano: " + e.getMessage());
        }
        return null;
    }

    public List<Plano> listar() {
        List<Plano> planos = new ArrayList<>();
        String sql = "SELECT * FROM plano WHERE ativo = 1";
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                planos.add(new Plano(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("valor"),
                        rs.getInt("duracao_meses")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar planos: " + e.getMessage());
        }
        return planos;
    }
}
