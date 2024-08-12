/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd;

import cadastrobd.model.*;
import cadastrobd.util.SequenceManager;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author maycon
 */
public class CadastroBDTeste {
    static String sequenciaID = "s_pessoa_id";
    public static void main(String[] args) {
        try {
            // Instanciar e persistir uma pessoa física
            PessoaFisica pessoaFisica = new PessoaFisica(
                SequenceManager.getValue(sequenciaID),
                "João Silva",
                "Rua A, 123",
                "123456789",
                "joao.silva@example.com",
                "12122233234"
            );
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            pessoaFisicaDAO.incluir(pessoaFisica);
            System.out.println("Pessoa Física incluída com sucesso.");

            // Alterar os dados da pessoa física
            pessoaFisica.setNome("João Silva Alterado");
            pessoaFisica.setEmail("joao.silva.alterado@example.com");
            pessoaFisicaDAO.alterar(pessoaFisica);
            System.out.println("Pessoa Física alterada com sucesso.");
            System.out.println("-----------------------------------");
            // Consultar todas as pessoas físicas e listar no console
            List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
            System.out.println("Lista de Pessoas Físicas:");
            for (PessoaFisica pf : pessoasFisicas) {
                pf.exibir();
            }

            // Excluir a pessoa física criada anteriormente
            pessoaFisicaDAO.excluir(pessoaFisica.getId());
            System.out.println("Pessoa Física excluída com sucesso.");

            // Instanciar e persistir uma pessoa jurídica
            PessoaJuridica pessoaJuridica = new PessoaJuridica(
                SequenceManager.getValue(sequenciaID),
                "Empresa XYZ",
                "Rua B, 456",
                "987654321",
                "contato@xyz.com",
                "18345656000199"
            );
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
            pessoaJuridicaDAO.incluir(pessoaJuridica);
            System.out.println("Pessoa Jurídica incluída com sucesso.");

            // Alterar os dados da pessoa jurídica
            pessoaJuridica.setNome("Empresa XYZ Alterada");
            pessoaJuridica.setEmail("contato.alterado@xyz.com");
            pessoaJuridicaDAO.alterar(pessoaJuridica);
            System.out.println("Pessoa Jurídica alterada com sucesso.");

            // Consultar todas as pessoas jurídicas e listar no console
            List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoas();
            System.out.println("Lista de Pessoas Jurídicas:");
            for (PessoaJuridica pj : pessoasJuridicas) {
                pj.exibir();
            }

            // Excluir a pessoa jurídica criada anteriormente
            pessoaJuridicaDAO.excluir(pessoaJuridica.getId());
            System.out.println("Pessoa Jurídica excluída com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
