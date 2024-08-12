package cadastrobd.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TesteUtil {
    public static void main(String[] args) {
        // Teste da classe ConectorBD
        try (Connection conn = ConectorBD.getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");

            String createTableSQL = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'teste') " +
                                    "CREATE TABLE teste (id INT PRIMARY KEY, nome VARCHAR(100))";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);
                System.out.println("Tabela 'teste' criada com sucesso!");
            }

            // Criar sequência para teste
            String createSequenceSQL = "IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name = 'teste_id_seq') " +
                                       "CREATE SEQUENCE teste_id_seq START WITH 1 INCREMENT BY 1";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createSequenceSQL);
                System.out.println("Sequência 'teste_id_seq' criada com sucesso!");
            }

            String insertSQL = "INSERT INTO teste (id, nome) VALUES (1, 'Nome Teste')";
            try (PreparedStatement ps = ConectorBD.getPrepared(insertSQL)) {
                ps.executeUpdate();
                System.out.println("Dados inseridos com sucesso!");
            }

            String selectSQL = "SELECT * FROM teste";
            try (ResultSet rs = ConectorBD.getSelect(selectSQL)) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Nome: " + rs.getString("nome"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Teste da classe SequenceManager
        try {
            int nextValue = SequenceManager.getValue("teste_id_seq");
            System.out.println("Próximo valor da sequência 'teste_id_seq': " + nextValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}