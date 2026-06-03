package br.ufersa.academia.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufersa.academia.database.ConexaoBanco;
import br.ufersa.academia.model.Aluno;
import br.ufersa.academia.model.Plano;

public class AlunoRepository {

    private final Connection conexao;

    public AlunoRepository() {
        this.conexao = ConexaoBanco.getInstancia().getConexao();
    }

    public boolean salvar(Aluno aluno) {
        String sqlUsuario = "INSERT INTO usuario (nome, email, senha, ativo, tipo) VALUES (?, ?, ?, 1, 'ALUNO')";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getSenha());
            stmt.executeUpdate();

            ResultSet chaves = stmt.getGeneratedKeys();
            if (chaves.next()) {
                int idGerado = chaves.getInt(1);
                aluno.setId(idGerado);

                String sqlAluno = "INSERT INTO aluno (id, matricula) VALUES (?, ?)";
                PreparedStatement stmtAluno = conexao.prepareStatement(sqlAluno);
                stmtAluno.setInt(1, idGerado);
                stmtAluno.setString(2, aluno.getMatricula());
                stmtAluno.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar aluno: " + e.getMessage());
        }
        return false;
    }

    public boolean atualizar(Aluno aluno) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getSenha());
            stmt.setInt(4, aluno.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar aluno: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarPlano(int alunoId, int planoId) {
        String sql = "UPDATE aluno SET plano_id = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, planoId);
            stmt.setInt(2, alunoId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar plano do aluno: " + e.getMessage());
            return false;
        }
    }

    public Aluno buscarPorId(int id) {
        String sql = """
            SELECT u.*, a.matricula, a.plano_id,
                   p.nome as plano_nome, p.valor as plano_valor, p.duracao_meses
            FROM usuario u
            JOIN aluno a ON u.id = a.id
            LEFT JOIN plano p ON a.plano_id = p.id
            WHERE u.id = ? AND u.ativo = 1
        """;
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return construirAluno(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar aluno: " + e.getMessage());
        }
        return null;
    }

    public List<Aluno> listar() {
        String sql = """
            SELECT u.*, a.matricula, a.plano_id,
                   p.nome as plano_nome, p.valor as plano_valor, p.duracao_meses
            FROM usuario u
            JOIN aluno a ON u.id = a.id
            LEFT JOIN plano p ON a.plano_id = p.id
            WHERE u.ativo = 1
        """;
        List<Aluno> alunos = new ArrayList<>();
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                alunos.add(construirAluno(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar alunos: " + e.getMessage());
        }
        return alunos;
    }

    private Aluno construirAluno(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("senha"),
                rs.getInt("ativo") == 1,
                rs.getString("matricula")
        );
        int planoId = rs.getInt("plano_id");
        if (planoId > 0 && rs.getString("plano_nome") != null) {
            Plano plano = new Plano(
                    planoId,
                    rs.getString("plano_nome"),
                    rs.getDouble("plano_valor"),
                    rs.getInt("duracao_meses")
            );
            aluno.setPlano(plano);
        }
        return aluno;
    }
}
