package com.mycompany.sistemalivraria;

import com.mycompany.sistemalivraria.controller.GerenciadorBiblioteca;
import com.mycompany.sistemalivraria.view.TelaLogin;

public class SistemaLivraria {
    public static void main(String[] args) {
        // 1. Criamos o gerenciador do sistema primeiro
        GerenciadorBiblioteca gerenciador = new GerenciadorBiblioteca();
        
        // 2. Colocamos o gerenciador dentro do parênteses da TelaLogin
        java.awt.EventQueue.invokeLater(() -> {
            new TelaLogin(gerenciador).setVisible(true);
        });
    }
}