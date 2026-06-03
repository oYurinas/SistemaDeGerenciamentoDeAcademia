package br.ufersa.academia.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBanco {

    private static final String URL = "jdbc:sqlite:academia.db";
    private static ConexaoBanco instancia;
    private Connection conexao;

    private ConexaoBanco() {
        inicializar();
    }

    public static ConexaoBanco getInstancia() {
        if (instancia == null) {
            instancia = new ConexaoBanco();
        }
        return instancia;
    }

    public Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                conexao = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter conexão: " + e.getMessage());
        }
        return conexao;
    }

    private void inicializar() {
        try {
            conexao = DriverManager.getConnection(URL);
            criarTabelas();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
        }
    }

    private void criarTabelas() throws SQLException {
        Statement stmt = conexao.createStatement();

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS usuario (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                senha TEXT NOT NULL,
                ativo INTEGER DEFAULT 1,
                tipo TEXT NOT NULL
            )
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS plano (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                valor REAL NOT NULL,
                duracao_meses INTEGER NOT NULL,
                ativo INTEGER DEFAULT 1
            )
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS aluno (
                id INTEGER PRIMARY KEY,
                matricula TEXT UNIQUE NOT NULL,
                plano_id INTEGER,
                FOREIGN KEY(id) REFERENCES usuario(id),
                FOREIGN KEY(plano_id) REFERENCES plano(id)
            )
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS personal_trainer (
                id INTEGER PRIMARY KEY,
                cref TEXT UNIQUE NOT NULL,
                FOREIGN KEY(id) REFERENCES usuario(id)
            )
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS treino (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                descricao TEXT NOT NULL,
                data_criacao TEXT NOT NULL,
                ativo INTEGER DEFAULT 1
            )
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS aluno_treino (
                aluno_id INTEGER NOT NULL,
                treino_id INTEGER NOT NULL,
                PRIMARY KEY(aluno_id, treino_id),
                FOREIGN KEY(aluno_id) REFERENCES aluno(id),
                FOREIGN KEY(treino_id) REFERENCES treino(id)
            )
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS pagamento (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                aluno_id INTEGER NOT NULL,
                valor REAL NOT NULL,
                data_pagamento TEXT NOT NULL,
                status TEXT NOT NULL,
                FOREIGN KEY(aluno_id) REFERENCES aluno(id)
            )
        """);

        stmt.close();
        inserirAdminPadrao();
    }

    private void inserirAdminPadrao() {
        try {
            Statement verificacao = conexao.createStatement();
            var resultado = verificacao.executeQuery(
                    "SELECT COUNT(*) as total FROM usuario WHERE tipo = 'ADMIN'"
            );
            if (resultado.getInt("total") == 0) {
                String senhaHash = br.ufersa.academia.service.PasswordUtil.hashSenha("admin123");
                PreparedStatement stmt = conexao.prepareStatement("""
                    INSERT INTO usuario (nome, email, senha, ativo, tipo)
                    VALUES ('Administrador', 'admin@academia.com', ?, 1, 'ADMIN')
                """);
                stmt.setString(1, senhaHash);
                stmt.execute();
                stmt.close();
                System.out.println("Admin padrão criado: admin@academia.com / admin123");
            }
            verificacao.close();
        } catch (SQLException e) {
            System.out.println("Erro ao criar admin padrão: " + e.getMessage());
        }
    }
}
