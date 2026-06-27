package com.mycompany.sistemalivraria.controller;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.sistemalivraria.model.Cliente;
import com.mycompany.sistemalivraria.model.Livro;
import com.mycompany.sistemalivraria.model.Locacao;
import com.mycompany.sistemalivraria.model.Usuario;

// Essa classe controla o fluxo de dados do sistema (Padrão MVC e Requisito RNF05)
public class GerenciadorBiblioteca {
    
    // Listas que funcionam como o "Banco de Dados em Memória" do sistema (RNF05)
    private List<Cliente> listaClientes;
    private List<Livro> listaLivros;
    private List<Locacao> listaLocacoes;
    private List<Usuario> listaUsuarios;

    // Construtor: Inicializa as listas vazias e cria dados de teste para facilitar
    public GerenciadorBiblioteca() {
        this.listaClientes = new ArrayList<>();
        this.listaLivros = new ArrayList<>();
        this.listaLocacoes = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();
        
        // Criando o usuário padrão do Bibliotecário para o login (RF01)
        this.listaUsuarios.add(new Usuario("admin", "1234"));
        
        // Dados de teste para o sistema não iniciar totalmente vazio (Opcional)
        inicializarDadosTeste();
    }

    // ==========================================
    // METODO DE LOGIN (RF01)
    // ==========================================
    public boolean realizarLogin(String login, String senha) {
        for (Usuario u : listaUsuarios) {
            if (u.getLogin().equals(login) && u.getSenha().equals(senha)) {
                return true; // Login aceito
            }
        }
        return false; // Login ou senha incorretos
    }

    // ==========================================
    // (RF02)
    // ==========================================
    public void cadastrarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public List<Cliente> listarClientes() {
        return listaClientes;
    }

    public void atualizarCliente(int id, String novoNome, String novoCpf, String novoEmail) {
        for (Cliente c : listaClientes) {
            if (c.getId() == id) {
                c.setNome(novoNome);
                c.setCpf(novoCpf);
                c.setEmail(novoEmail);
                break;
            }
        }
    }

    public void removerCliente(int id) {
        // Remove da lista se o ID bater
        listaClientes.removeIf(c -> c.getId() == id);
    }

    // ==========================================
    //  (RF03)
    // ==========================================
    public void cadastrarLivro(Livro livro) {
        listaLivros.add(livro);
    }

    public List<Livro> listarLivros() {
        return listaLivros;
    }

    public void atualizarLivro(int id, String novoTitulo, String novaEditora, int novoAno, int novasPaginas, int novoEstoque) {
        for (Livro l : listaLivros) {
            if (l.getId() == id) {
                l.setTitulo(novoTitulo);
                l.setEditora(novaEditora);
                l.setAnoPublicacao(novoAno);
                l.setNumPaginas(novasPaginas);
                l.setEstoque(novoEstoque);
                break;
            }
        }
    }

    public void removerLivro(int id) {
        listaLivros.removeIf(l -> l.getId() == id);
    }

    // ==========================================
    // GERENCIAMENTO DE LOCAÇÕES (RF05, RF07, RF08)
    // ==========================================
    public void registrarNovaLocacao(Locacao locacao) {
        listaLocacoes.add(locacao);
    }

    public List<Locacao> listarLocacoes() {
        return listaLocacoes;
    }

    // Método auxiliar para gerar ID incremental automático para os cadastros
    public int gerarProximoIdCliente() { return listaClientes.size() + 1; }
    public int gerarProximoIdLivro() { return listaLivros.size() + 1; }
    public int gerarProximoIdLocacao() { return listaLocacoes.size() + 1; }

    // Cria alguns registros iniciais para poupar tempo de digitação nos testes
    private void inicializarDadosTeste() {
        // Cadastrando alguns clientes fictícios
        listaClientes.add(new Cliente(1, "Maria Clara", "111.222.333-44", "joao@email.com"));
        listaClientes.add(new Cliente(2, "Giovanni Fernandes", "555.666.777-88", "maria@email.com"));
        
        // Importando subclasses usando o polimorfismo do pacote model correto
        listaLivros.add(new com.mycompany.sistemalivraria.model.LivroLiteratura(1, "Dom Casmurro", "Caramuru", 1899, 256, 5));
        listaLivros.add(new com.mycompany.sistemalivraria.model.LivroTecnico(2, "Java: Como Programar", "Pearson", 2016, 960, 3));
    }
}