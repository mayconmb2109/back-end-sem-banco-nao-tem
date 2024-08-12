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
public class PessoaJuridicaDAO {
    public PessoaJuridica getPessoa(int id) throws SQLException {
        String sql = "SELECT p.id_pessoa, p.nome, p.endereco, p.telefone, p.email, pj.cnpj " +
                     "FROM Pessoa p INNER JOIN PessoaJuridica pj ON p.id_pessoa = pj.id_pessoa WHERE p.id_pessoa = ?";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PessoaJuridica(
                        rs.getInt("id_pessoa"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cnpj")
                    );
                }
            }
        }
        return null;
    }

    public List<PessoaJuridica> getPessoas() throws SQLException {
        List<PessoaJuridica> pessoas = new ArrayList<>();
        String sql = "SELECT p.id_pessoa, p.nome, p.endereco, p.telefone, p.email, pj.cnpj " +
                     "FROM Pessoa p INNER JOIN PessoaJuridica pj ON p.id_pessoa = pj.id_pessoa";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pessoas.add(new PessoaJuridica(
                    rs.getInt("id_pessoa"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cnpj")
                ));
            }
        }
        return pessoas;
    }

    public void incluir(PessoaJuridica pessoa) throws SQLException {
        String sqlPessoa = "INSERT INTO Pessoa (id_pessoa, nome, tipo, endereco, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPessoaJuridica = "INSERT INTO PessoaJuridica (id_pessoa, cnpj) VALUES (?, ?)";        
        pessoa.setTipo("J");
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa);
             PreparedStatement psPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica)) {
            conn.setAutoCommit(false);

            psPessoa.setInt(1, pessoa.getId());
            psPessoa.setString(2, pessoa.getNome());
            psPessoa.setString(3, pessoa.getTipo());
            psPessoa.setString(4, pessoa.getEndereco());            
            psPessoa.setString(5, pessoa.getTelefone());
            psPessoa.setString(6, pessoa.getEmail());
            psPessoa.executeUpdate();

            psPessoaJuridica.setInt(1, pessoa.getId());
            psPessoaJuridica.setString(2, pessoa.getCnpj());
            psPessoaJuridica.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void alterar(PessoaJuridica pessoa) throws SQLException {
        String sqlPessoa = "UPDATE Pessoa SET nome = ?, endereco = ?, telefone = ?, email = ? WHERE id_pessoa = ?";
        String sqlPessoaJuridica = "UPDATE PessoaJuridica SET cnpj = ? WHERE id_pessoa = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa);
             PreparedStatement psPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica)) {
            conn.setAutoCommit(false);

            psPessoa.setString(1, pessoa.getNome());
            psPessoa.setString(2, pessoa.getEndereco());            
            psPessoa.setString(3, pessoa.getTelefone());
            psPessoa.setString(4, pessoa.getEmail());
            psPessoa.setInt(5, pessoa.getId());
            psPessoa.executeUpdate();

            psPessoaJuridica.setString(1, pessoa.getCnpj());
            psPessoaJuridica.setInt(2, pessoa.getId());
            psPessoaJuridica.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void excluir(int id) throws SQLException {
        String sqlPessoaJuridica = "DELETE FROM PessoaJuridica WHERE id_pessoa = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE id_pessoa = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement psPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica);
             PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa)) {
            conn.setAutoCommit(false);

            psPessoaJuridica.setInt(1, id);
            psPessoaJuridica.executeUpdate();

            psPessoa.setInt(1, id);
            psPessoa.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}