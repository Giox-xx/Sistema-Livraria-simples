package com.mycompany.sistemalivraria.model;

// Aplicação de HERANÇA: LivroTecnico também é um tipo de Livro
public class LivroTecnico extends Livro {
    
    // Construtor que inicializa a classe mãe
    public LivroTecnico(int id, String titulo, String editora, int anoPublicacao, int numPaginas, int estoque) {
        super(id, titulo, editora, anoPublicacao, numPaginas, estoque);
    }

    // Aplicação de POLIMORFISMO (Requisito RF06): Comportamento diferente para livro técnico
    @Override
    public double calcularDiaria() {
        // Conforme o RF06, livros técnicos cobram a taxa base (5.00) + um adicional de R$ 2,00
        return 5.00 + 2.00; 
    }
}