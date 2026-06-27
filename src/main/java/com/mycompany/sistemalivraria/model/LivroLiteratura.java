package com.mycompany.sistemalivraria.model;

// O "extends" indica HERANÇA. LivroLiteratura herda tudo de Livro (Requisito RNF02)
public class LivroLiteratura extends Livro {
    
    // Construtor: Repassa os dados para a classe mãe (Livro) usando o "super"
    public LivroLiteratura(int id, String titulo, String editora, int anoPublicacao, int numPaginas, int estoque) {
        super(id, titulo, editora, anoPublicacao, numPaginas, estoque);
    }

    // @Override indica SOBRESCRITA de método (POLIMORFISMO do Requisito RF06)
    @Override
    public double calcularDiaria() {
        // Conforme o RF06, livros de literatura cobram uma diária fixa de R$ 5,00
        return 5.00; 
    }
}