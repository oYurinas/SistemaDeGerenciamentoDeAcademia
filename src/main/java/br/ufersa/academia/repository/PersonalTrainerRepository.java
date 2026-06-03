package br.ufersa.academia.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufersa.academia.database.ConexaoBanco;
import br.ufersa.academia.model.PersonalTrainer;

public class PersonalTrainerRepository {

    private final Connection conexao;

    public PersonalTrainerRepository() {
        this.conexao = ConexaoBanco.getInstancia().getConexao();
    }

    public boolean salvar(PersonalTrainer personal) {
        String sqlUsuario = "INSERT INTO usuario (nome, email, senha, ativo, tipo) VALUES (?, ?, ?, 1, 'PERSONAL')";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, personal.getNome());
            stmt.setString(2, personal.getEmail());
            stmt.setString(3, personal.getSenha());
            stmt.executeUpdate();

            ResultSet chaves = stmt.getGeneratedKeys();
            if (chaves.next()) {
                int idGerado = chaves.getInt(1);
                personal.setId(idGerado);

                String sqlPersonal = "INSERT INTO personal_trainer (id, cref) VALUES (?, ?)";
                PreparedStatement stmtPersonal = conexao.prepareStatement(sqlPersonal);
                stmtPersonal.setInt(1, idGerado);
                stmtPersonal.setString(2, personal.getCref());
                stmtPersonal.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar personal trainer: " + e.getMessage());
        }
        return false;
    }

    public boolean atualizar(PersonalTrainer personal) {
        String sqlUsuario = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sqlUsuario);
            stmt.setString(1, personal.getNome());
            stmt.setString(2, personal.getEmail());
            stmt.setString(3, personal.getSenha());
            stmt.setInt(4, personal.getId());
            stmt.executeUpdate();

            String sqlPersonal = "UPDATE personal_trainer SET cref = ? WHERE id = ?";
            PreparedStatement stmtPersonal = conexao.prepareStatement(sqlPersonal);
            stmtPersonal.setString(1, personal.getCref());
            stmtPersonal.setInt(2, personal.getId());
            return stmtPersonal.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar personal trainer: " + e.getMessage());
            return false;
        }
    }

    public PersonalTrainer buscarPorId(int id) {
        String sql = """
            SELECT u.*, pt.cref
            FROM usuario u
            JOIN personal_trainer pt ON u.id = pt.id
            WHERE u.id = ? AND u.ativo = 1
        """;
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return construirPersonal(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar personal trainer: " + e.getMessage());
        }
        return null;
    }

    public List<PersonalTrainer> listar() {
        String sql = """
            SELECT u.*, pt.cref
            FROM usuario u
            JOIN personal_trainer pt ON u.id = pt.id
            WHERE u.ativo = 1
        """;
        List<PersonalTrainer> lista = new ArrayList<>();
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                lista.add(construirPersonal(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar personal trainers: " + e.getMessage());
        }
        return lista;
    }

    private PersonalTrainer construirPersonal(ResultSet rs) throws SQLException {
        return new PersonalTrainer(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("senha"),
                rs.getInt("ativo") == 1,
                rs.getString("cref")
        );
    }
}
