package com.mycompany.sistemalivraria.model;

public abstract class Livro {
    private int id;
    private String titulo;
    private String editora;
    private int anoPublicacao;
    private int numPaginas;
    private int estoque; // Estoque físico total na loja
    private int reservado; // Quantos foram pedidos/reservados pelo site

    public Livro(int id, String titulo, String editora, int anoPublicacao, int numPaginas, int estoque) {
        this.id = id;
        this.titulo = titulo;
        this.editora = editora;
        this.anoPublicacao = anoPublicacao;
        this.numPaginas = numPaginas;
        this.estoque = estoque;
        this.reservado = 0; // Todo livro começa com 0 reservas
    }

    public abstract double calcularDiaria();

    // Métodos de controle de estoque e reserva
    public void reservarLivro() {
        if (getEstoqueDisponivel() > 0) {
            this.reservado++;
        }
    }

    public void confirmarRetirada() {
        if (this.reservado > 0) {
            this.estoque--;   // Sai do estoque físico
            this.reservado--; // Sai da reserva
        }
    }

    public void cancelarReserva() {
        if (this.reservado > 0) {
            this.reservado--; // Volta a ficar disponível
        }
    }

    public int getEstoqueDisponivel() {
        return this.estoque - this.reservado;
    }
    
    public void devolverAoEstoque() { 
        this.estoque++; 
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }
    public int getNumPaginas() { return numPaginas; }
    public void setNumPaginas(int numPaginas) { this.numPaginas = numPaginas; }
    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }
    public int getReservado() { return reservado; }
    public void setReservado(int reservado) { this.reservado = reservado; }

    @Override
    public String toString() {
        return titulo + " (Disponível: " + getEstoqueDisponivel() + ")";
    }
}