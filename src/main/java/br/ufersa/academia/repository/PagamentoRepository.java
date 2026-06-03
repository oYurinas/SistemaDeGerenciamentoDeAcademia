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
import br.ufersa.academia.model.Pagamento;

public class PagamentoRepository {

    private final Connection conexao;

    public PagamentoRepository() {
        this.conexao = ConexaoBanco.getInstancia().getConexao();
    }

    public boolean salvar(Pagamento pagamento) {
        String sql = "INSERT INTO pagamento (aluno_id, valor, data_pagamento, status) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, pagamento.getAlunoId());
            stmt.setDouble(2, pagamento.getValor());
            stmt.setString(3, pagamento.getDataPagamento().toString());
            stmt.setString(4, pagamento.getStatus());
            stmt.executeUpdate();
            ResultSet chaves = stmt.getGeneratedKeys();
            if (chaves.next()) {
                pagamento.setId(chaves.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar pagamento: " + e.getMessage());
            return false;
        }
    }

    public List<Pagamento> listarPorAluno(int alunoId) {
        String sql = "SELECT * FROM pagamento WHERE aluno_id = ? ORDER BY data_pagamento DESC";
        List<Pagamento> pagamentos = new ArrayList<>();
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, alunoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pagamentos.add(new Pagamento(
                        rs.getInt("id"),
                        rs.getInt("aluno_id"),
                        rs.getDouble("valor"),
                        LocalDate.parse(rs.getString("data_pagamento")),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pagamentos do aluno: " + e.getMessage());
        }
        return pagamentos;
    }

    public List<Pagamento> listar() {
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT * FROM pagamento ORDER BY data_pagamento DESC";
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                pagamentos.add(new Pagamento(
                        rs.getInt("id"),
                        rs.getInt("aluno_id"),
                        rs.getDouble("valor"),
                        LocalDate.parse(rs.getString("data_pagamento")),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pagamentos: " + e.getMessage());
        }
        return pagamentos;
    }

    public boolean alunoEstaAdimplente(int alunoId) {
        String sql = """
            SELECT COUNT(*) as total FROM pagamento
            WHERE aluno_id = ? AND status = 'PAGO'
            AND data_pagamento >= date('now', '-30 days')
        """;
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, alunoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar adimplência: " + e.getMessage());
        }
        return false;
    }
}
