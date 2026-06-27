package com.mycompany.sistemalivraria.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Locacao {
    private int id;
    private Cliente cliente; 
    private List<Livro> livros; 
    private LocalDate dataLocacao;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataRealDevolucao;
    private double valorTotal;
    private double multa;
    private boolean ativa;
    private boolean retiradaConfirmada; // Sabe se o cliente já buscou o livro físico

    public Locacao(int id, Cliente cliente, LocalDate dataLocacao, int diasParaDevolucao) {
        this.id = id;
        this.cliente = cliente;
        this.livros = new ArrayList<>();
        this.dataLocacao = dataLocacao;
        this.dataPrevistaDevolucao = dataLocacao.plusDays(diasParaDevolucao);
        this.ativa = true;
        this.multa = 0.0;
        this.valorTotal = 0.0;
        this.retiradaConfirmada = false; // Começa apenas como reserva
    }

  public boolean adicionarLivro(Livro livro) {
    if (livro.getEstoqueDisponivel() > 0) {
        this.livros.add(livro);
        livro.reservarLivro(); 
        return true; // Retorna true se conseguiu reservar
    }
    return false; // Retorna false se não tinha estoque disponível
}

    public void confirmarRetiradaPresencial() {
        if (this.ativa && !this.retiradaConfirmada) {
            this.retiradaConfirmada = true;
            for (Livro livro : livros) {
                livro.confirmarRetirada(); 
            }
        }
    }

    public void cancelarLocacaoAntesDaRetirada() {
        if (this.ativa && !this.retiradaConfirmada) {
            this.ativa = false;
            for (Livro livro : livros) {
                livro.cancelarReserva(); 
            }
        }
    }

    public void calcularValorInicial() {
        long dias = ChronoUnit.DAYS.between(dataLocacao, dataPrevistaDevolucao);
        if (dias <= 0) dias = 1;
        
        double total = 0;
        for (Livro livro : livros) {
            total += livro.calcularDiaria() * dias; 
        }
        this.valorTotal = total;
    }

    public void registrarDevolucao(LocalDate dataReal) {
        this.dataRealDevolucao = dataReal;
        this.ativa = false;

        if (this.retiradaConfirmada) {
            for (Livro livro : livros) {
                livro.devolverAoEstoque();
            }
        } else {
            for (Livro livro : livros) {
                livro.cancelarReserva();
            }
        }

        if (dataRealDevolucao.isAfter(dataPrevistaDevolucao)) {
            long diasAtraso = ChronoUnit.DAYS.between(dataPrevistaDevolucao, dataRealDevolucao);
            double valorDiariaBase = 5.00;
            this.multa = diasAtraso * valorDiariaBase;
            this.valorTotal += this.multa;
        }
    }

    // Getters e Setters
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<Livro> getLivros() { return livros; }
    public LocalDate getDataLocacao() { return dataLocacao; }
    public LocalDate getDataPrevistaDevolucao() { return dataPrevistaDevolucao; }
    public LocalDate getDataRealDevolucao() { return dataRealDevolucao; }
    public double getValorTotal() { return valorTotal; }
    public double getMulta() { return multa; }
    public boolean isAtiva() { return ativa; }
    public boolean isRetiradaConfirmada() { return retiradaConfirmada; }
}