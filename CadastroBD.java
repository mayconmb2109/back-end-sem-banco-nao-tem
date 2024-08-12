/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cadastrobd;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaJuridicaDAO;
import cadastrobd.util.SequenceManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maycon
 */

public class CadastroBD {
    static String sequenciaID = "s_pessoa_id";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
        
        while (true) {
            try {
                System.out.println("====== Escolha uma opção ======");
                System.out.println("1 - Incluir");
                System.out.println("2 - Alterar");
                System.out.println("3 - Excluir");
                System.out.println("4 - Exibir pelo ID");
                System.out.println("5 - Exibir todos");
                System.out.println("0 - Sair");
                System.out.println("===============================");
                System.out.print("Opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine(); 
                
                if (opcao == 0) {
                    System.out.println("Saindo...");
                    break;
                }
                
                switch (opcao) {
                    case 1:
                        incluir(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    case 2:
                        alterar(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    case 3:
                        excluir(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    case 4:
                        exibirPorId(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    case 5:
                        exibirTodos(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(CadastroBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        scanner.close();
    }

    private static void incluir(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) throws SQLException {
        System.out.print("Incluir Pessoa Física ou Jurídica? (F/J): ");
        String tipo = scanner.nextLine().toUpperCase();
        switch (tipo) {
            case "F":
                {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Endereço: ");
                    String endereco = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    PessoaFisica pessoaFisica = new PessoaFisica(SequenceManager.getValue(sequenciaID), nome, endereco, telefone, email, cpf);
                    pessoaFisicaDAO.incluir(pessoaFisica);
                    System.out.println("Pessoa Física incluída com sucesso!");
                    break;
                }
            case "J":
                {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Endereço: ");
                    String endereco = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("CNPJ: ");
                    String cnpj = scanner.nextLine();
                    PessoaJuridica pessoaJuridica = new PessoaJuridica(SequenceManager.getValue(sequenciaID), nome, endereco, telefone, email, cnpj);
                    pessoaJuridicaDAO.incluir(pessoaJuridica);
                    System.out.println("Pessoa Jurídica incluída com sucesso!");
                    break;
                }
            default:
                System.out.println("Tipo inválido!");
                break;
        }
    }

    private static void alterar(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) throws SQLException {
        System.out.print("Alterar Pessoa Física ou Jurídica? (F/J): ");
        String tipo = scanner.nextLine().toUpperCase();
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        switch (tipo) {
            case "F":
                PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id);
                if (pessoaFisica != null) {
                    System.out.println("Dados atuais:");
                    pessoaFisica.exibir();
                    
                    System.out.print("Novo nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Novo endereço: ");
                    String endereco = scanner.nextLine();
                    
                    System.out.print("Novo telefone: ");
                    String telefone = scanner.nextLine();
                    System.out.print("Novo email: ");
                    String email = scanner.nextLine();
                    System.out.print("Novo CPF: ");
                    String cpf = scanner.nextLine();
                    
                    pessoaFisica.setNome(nome);
                    pessoaFisica.setEndereco(endereco);
                    pessoaFisica.setTelefone(telefone);
                    pessoaFisica.setEmail(email);
                    pessoaFisica.setCpf(cpf);
                    pessoaFisicaDAO.alterar(pessoaFisica);
                    System.out.println("Pessoa Física alterada com sucesso!");
                } else {
                    System.out.println("Pessoa Física não encontrada!");
                }   break;
            case "J":
                PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);
                if (pessoaJuridica != null) {
                    System.out.println("Dados atuais:");
                    pessoaJuridica.exibir();
                    
                    System.out.print("Novo nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Novo endereço: ");
                    String endereco = scanner.nextLine();
                    System.out.print("Novo telefone: ");
                    String telefone = scanner.nextLine();
                    System.out.print("Novo email: ");
                    String email = scanner.nextLine();
                    System.out.print("Novo CNPJ: ");
                    String cnpj = scanner.nextLine();
                    
                    pessoaJuridica.setNome(nome);
                    pessoaJuridica.setEndereco(endereco);
                    pessoaJuridica.setTelefone(telefone);
                    pessoaJuridica.setEmail(email);
                    pessoaJuridica.setCnpj(cnpj);
                    pessoaJuridicaDAO.alterar(pessoaJuridica);
                    System.out.println("Pessoa Jurídica alterada com sucesso!");
                } else {
                    System.out.println("Pessoa Jurídica não encontrada!");
                }   break;
            default:
                System.out.println("Tipo inválido!");
                break;
        }
    }

    private static void excluir(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) throws SQLException {
        System.out.print("Excluir Pessoa Física ou Jurídica? (F/J): ");
        String tipo = scanner.nextLine().toUpperCase();
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        switch (tipo) {
            case "F":
                pessoaFisicaDAO.excluir(id);
                System.out.println("Pessoa Física excluída com sucesso!");
                break;
            case "J":
                pessoaJuridicaDAO.excluir(id);
                System.out.println("Pessoa Jurídica excluída com sucesso!");
                break;
            default:
                System.out.println("Tipo inválido!");
                break;
        }
    }

    private static void exibirPorId(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) throws SQLException {
        System.out.print("Exibir Pessoa Física ou Jurídica? (F/J): ");
        String tipo = scanner.nextLine().toUpperCase();
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        switch (tipo) {
            case "F":
                PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id);
                if (pessoaFisica != null) {
                    pessoaFisica.exibir();
                } else {
                    System.out.println("Pessoa Física não encontrada!");
                }   break;
            case "J":
                PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);
                if (pessoaJuridica != null) {
                    pessoaJuridica.exibir();
                } else {
                    System.out.println("Pessoa Jurídica não encontrada!");
                }   break;
            default:
                System.out.println("Tipo inválido!");
                break;
        }
    }

    private static void exibirTodos(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) throws SQLException {
        System.out.print("Exibir todas as Pessoas Físicas ou Jurídicas? (F/J): ");
        String tipo = scanner.nextLine().toUpperCase();

        switch (tipo) {
            case "F":
                System.out.println("Exibindo dados de Pessoa Fisica...");
                System.out.println("===================================");
                pessoaFisicaDAO.getPessoas().forEach(PessoaFisica::exibir);
                break;
            case "J":
                System.out.println("Exibindo dados de Pessoa Jurídica1...");
                System.out.println("===================================");
                pessoaJuridicaDAO.getPessoas().forEach(PessoaJuridica::exibir);
                break;
            default:
                System.out.println("Tipo inválido!");
                break;
        }
    }
}
