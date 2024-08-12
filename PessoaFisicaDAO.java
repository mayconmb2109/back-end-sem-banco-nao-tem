/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;

import cadastrobd.util.ConectorBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maycon
 */
public class PessoaFisicaDAO {
    public PessoaFisica getPessoa(int id) throws SQLException {
        String sql = "SELECT p.id_pessoa, p.nome, p.endereco, p.telefone, p.email, pf.cpf " +
                     "FROM Pessoa p INNER JOIN PessoaFisica pf ON p.id_pessoa = pf.id_pessoa WHERE p.id_pessoa = ?";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PessoaFisica(
                        rs.getInt("id_pessoa"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cpf")
                    );
                }
            }
        }
        return null;
    }

    public List<PessoaFisica> getPessoas() throws SQLException {
        List<PessoaFisica> pessoas = new ArrayList<>();
        String sql = "SELECT p.id_pessoa, p.nome, p.endereco, p.telefone, p.email, pf.cpf " +
                     "FROM Pessoa p INNER JOIN PessoaFisica pf ON p.id_pessoa = pf.id_pessoa";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pessoas.add(new PessoaFisica(
                    rs.getInt("id_pessoa"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cpf")
                ));
            }
        }
        return pessoas;
    }

    public void incluir(PessoaFisica pessoa) throws SQLException {
        String sqlPessoa = "INSERT INTO Pessoa (id_pessoa, nome, tipo, endereco, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPessoaFisica = "INSERT INTO PessoaFisica (id_pessoa, cpf) VALUES (?, ?)";        
        pessoa.setTipo("F");
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa);
             PreparedStatement psPessoaFisica = conn.prepareStatement(sqlPessoaFisica)) {
            conn.setAutoCommit(false);

            psPessoa.setInt(1, pessoa.getId());
            psPessoa.setString(2, pessoa.getNome());
            psPessoa.setString(3, pessoa.getTipo());
            psPessoa.setString(4, pessoa.getEndereco());
            psPessoa.setString(5, pessoa.getTelefone());
            psPessoa.setString(6, pessoa.getEmail());
            psPessoa.executeUpdate();

            psPessoaFisica.setInt(1, pessoa.getId());
            psPessoaFisica.setString(2, pessoa.getCpf());
            psPessoaFisica.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void alterar(PessoaFisica pessoa) throws SQLException {
        String sqlPessoa = "UPDATE Pessoa SET nome = ?, endereco = ?, telefone = ?, email = ? WHERE id_pessoa = ?";
        String sqlPessoaFisica = "UPDATE PessoaFisica SET cpf = ? WHERE id_pessoa = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa);
             PreparedStatement psPessoaFisica = conn.prepareStatement(sqlPessoaFisica)) {
            conn.setAutoCommit(false);

            psPessoa.setString(1, pessoa.getNome());
            psPessoa.setString(2, pessoa.getEndereco());
            psPessoa.setString(3, pessoa.getTelefone());
            psPessoa.setString(4, pessoa.getEmail());
            psPessoa.setInt(5, pessoa.getId());
            psPessoa.executeUpdate();

            psPessoaFisica.setString(1, pessoa.getCpf());
            psPessoaFisica.setInt(2, pessoa.getId());
            psPessoaFisica.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void excluir(int id) throws SQLException {
        String sqlPessoaFisica = "DELETE FROM PessoaFisica WHERE id_pessoa = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE id_pessoa = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement psPessoaFisica = conn.prepareStatement(sqlPessoaFisica);
             PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa)) {
            conn.setAutoCommit(false);

            psPessoaFisica.setInt(1, id);
            psPessoaFisica.executeUpdate();

            psPessoa.setInt(1, id);
            psPessoa.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}