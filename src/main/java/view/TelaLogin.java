package com.mycompany.sistemalivraria.view;

import com.mycompany.sistemalivraria.controller.GerenciadorBiblioteca;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class TelaLogin extends JFrame {
    private GerenciadorBiblioteca gerenciador;
    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    public TelaLogin(GerenciadorBiblioteca gerenciador) {
        this.gerenciador = gerenciador;
        
        // Configurações básicas da janela
        setTitle("Acesso ao Sistema");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel Principal 
        JPanel painelFundo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                // Degradê do azul escuro para o quase preto
                GradientPaint gp = new GradientPaint(0, 0, new Color(24, 28, 43), 0, getHeight(), new Color(15, 15, 26));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        // Título Principal
        JLabel lblTitulo = new JLabel("LIVRARIA POO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(0, 210, 255)); // Ciano brilhante
        lblTitulo.setBounds(0, 50, 450, 40);
        painelFundo.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Painel de Controle", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(150, 160, 175));
        lblSubtitulo.setBounds(0, 90, 450, 20);
        painelFundo.add(lblSubtitulo);

        // Card do Login 
        JPanel cardLogin = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(33, 38, 57)); // Fundo do card ligeiramente mais claro
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        cardLogin.setBounds(50, 140, 350, 300);
        cardLogin.setLayout(null);
        cardLogin.setOpaque(false);
        painelFundo.add(cardLogin);

        // Campo Usuário
        JLabel lblUsuario = new JLabel("USUÁRIO");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblUsuario.setForeground(new Color(0, 210, 255));
        lblUsuario.setBounds(30, 30, 290, 20);
        cardLogin.add(lblUsuario);

        txtUsuario = new JTextField("admin");
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario.setBackground(new Color(23, 26, 40));
        txtUsuario.setForeground(Color.WHITE);
        txtUsuario.setCaretColor(Color.WHITE);
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 60, 85), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtUsuario.setBounds(30, 55, 290, 35);
        cardLogin.add(txtUsuario);

        // Campo Senha
        JLabel lblSenha = new JLabel("SENHA");
        lblSenha.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblSenha.setForeground(new Color(0, 210, 255));
        lblSenha.setBounds(30, 110, 290, 20);
        cardLogin.add(lblSenha);

        txtSenha = new JPasswordField("1234");
        txtSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenha.setBackground(new Color(23, 26, 40));
        txtSenha.setForeground(Color.WHITE);
        txtSenha.setCaretColor(Color.WHITE);
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 60, 85), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtSenha.setBounds(30, 135, 290, 35);
        cardLogin.add(txtSenha);

        // Botão Login
        JButton btnLogin = new JButton("ENTRAR") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 210, 255), getWidth(), 0, new Color(0, 130, 255));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setBounds(30, 210, 290, 45);
        cardLogin.add(btnLogin);

        //  botão de clique
        btnLogin.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String senha = new String(txtSenha.getPassword());

            // Validação simples batendo com seu sistema anterior
            if (usuario.equals("admin") && senha.equals("1234")) {
                dispose(); // Fecha o login
                new TelaPrincipal(gerenciador).setVisible(true); // Abre a principal completa
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos!", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}