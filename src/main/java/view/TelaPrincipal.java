package com.mycompany.sistemalivraria.view;

import com.mycompany.sistemalivraria.controller.GerenciadorBiblioteca;
import com.mycompany.sistemalivraria.model.Cliente;
import com.mycompany.sistemalivraria.model.Livro;
import com.mycompany.sistemalivraria.model.Locacao;
import com.mycompany.sistemalivraria.model.LivroLiteratura;
import com.mycompany.sistemalivraria.model.LivroTecnico;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private GerenciadorBiblioteca gerenciador;
    private JTabbedPane abas;
    
    private JTextArea txtListaClientes;
    private JTextArea txtListaLivros;
    private JTextArea txtListaLocacoes;

    // Cores do Tema Dark Moderno
    private final Color COR_FUNDO_DARK = new Color(15, 15, 26);
    private final Color COR_CARD_DARK = new Color(24, 28, 43);
    private final Color COR_TEXTO_CAMPO = new Color(23, 26, 40);
    private final Color COR_CIANO = new Color(0, 210, 255);
    private final Color COR_TEXTO_MUTED = new Color(150, 160, 175);

    public TelaPrincipal(GerenciadorBiblioteca gerenciador) {
        this.gerenciador = gerenciador;
        setTitle("Sistema de Gerenciamento de Livraria - Painel Geral");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Customização das abas para combinar com o tema escuro
        UIManager.put("TabbedPane.background", COR_CARD_DARK);
        UIManager.put("TabbedPane.foreground", Color.WHITE);
        UIManager.put("TabbedPane.selected", COR_FUNDO_DARK);
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(1, 1, 1, 1));

        abas = new JTabbedPane();
        abas.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        configurarAbaClientes();
        configurarAbaLivros();
        configurarAbaLocacoes();
        add(abas);
    }

    // Estiliza os campos de texto do formulário
    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBackground(COR_TEXTO_CAMPO);
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 60, 85), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    // Estiliza os labels textuais
    private JLabel criarLabelForm(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(COR_CIANO);
        return label;
    }

    // Cria os botões com gradiente moderno
    private JButton criarBotaoModerno(String texto) {
        JButton botao = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, COR_CIANO, getWidth(), 0, new Color(0, 130, 255));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        botao.setFont(new Font("Segoe UI", Font.BOLD, 13));
        botao.setForeground(Color.WHITE);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    // Estiliza as caixas de listagem (Output)
    private void estilizarAreaLista(JTextArea area) {
        area.setEditable(false);
        area.setBackground(COR_TEXTO_CAMPO);
        area.setForeground(new Color(200, 210, 230));
        area.setFont(new Font("Consolas", Font.PLAIN, 13));
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private TitledBorder criarBordaPainel(String titulo) {
        TitledBorder borda = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(50, 60, 85), 1), titulo);
        borda.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        borda.setTitleColor(Color.WHITE);
        return borda;
    }

    // ==========================================
    // ABA 1: CLIENTES (DARK MODE)
    // ==========================================
    private void configurarAbaClientes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBackground(COR_FUNDO_DARK);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JPanel painelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        painelForm.setBackground(COR_CARD_DARK);
        painelForm.setBorder(criarBordaPainel(" Cadastrar Novo Cliente "));

        JTextField txtNome = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtEmail = new JTextField();

        estilizarCampo(txtNome);
        estilizarCampo(txtCpf);
        estilizarCampo(txtEmail);

        painelForm.add(criarLabelForm("  Nome:"));
        painelForm.add(txtNome);
        painelForm.add(criarLabelForm("  CPF:"));
        painelForm.add(txtCpf);
        painelForm.add(criarLabelForm("  Email:"));
        painelForm.add(txtEmail);

        JButton btnSalvarCliente = criarBotaoModerno("CADASTRAR CLIENTE");
        painelForm.add(new JLabel());
        painelForm.add(btnSalvarCliente);

        txtListaClientes = new JTextArea();
        estilizarAreaLista(txtListaClientes);
        atualizarListaDeClientes();

        btnSalvarCliente.addActionListener(e -> {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String email = txtEmail.getText();

            if (nome.isEmpty() || cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios!", "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = gerenciador.gerarProximoIdCliente();
            Cliente novoCliente = new Cliente(id, nome, cpf, email);
            gerenciador.cadastrarCliente(novoCliente);

            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            atualizarListaDeClientes();

            txtNome.setText("");
            txtCpf.setText("");
            txtEmail.setText("");
        });

        painelPrincipal.add(painelForm, BorderLayout.NORTH);
        
        JScrollPane scroll = new JScrollPane(txtListaClientes);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(50, 60, 85)));
        painelPrincipal.add(scroll, BorderLayout.CENTER);
        
        abas.addTab("Clientes", painelPrincipal);
    }

    // ==========================================
    // ABA 2: LIVROS (DARK MODE)
    // ==========================================
    private void configurarAbaLivros() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBackground(COR_FUNDO_DARK);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JPanel painelForm = new JPanel(new GridLayout(7, 2, 8, 8));
        painelForm.setBackground(COR_CARD_DARK);
        painelForm.setBorder(criarBordaPainel(" Cadastrar Novo Livro "));

        JTextField txtTitulo = new JTextField();
        JTextField txtEditora = new JTextField();
        JTextField txtAno = new JTextField();
        JTextField txtPaginas = new JTextField();
        JTextField txtEstoque = new JTextField();
        
        JCheckBox chkIsLiteratura = new JCheckBox("Categoria Literatura (Taxa: R$ 5,00/dia)");
        chkIsLiteratura.setBackground(COR_CARD_DARK);
        chkIsLiteratura.setForeground(Color.WHITE);
        chkIsLiteratura.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkIsLiteratura.setSelected(true);

        estilizarCampo(txtTitulo);
        estilizarCampo(txtEditora);
        estilizarCampo(txtAno);
        estilizarCampo(txtPaginas);
        estilizarCampo(txtEstoque);

        painelForm.add(criarLabelForm("  Título:"));
        painelForm.add(txtTitulo);
        painelForm.add(criarLabelForm("  Editora:"));
        painelForm.add(txtEditora);
        painelForm.add(criarLabelForm("  Ano de Publicação:"));
        painelForm.add(txtAno);
        painelForm.add(criarLabelForm("  Nº de Páginas:"));
        painelForm.add(txtPaginas);
        painelForm.add(criarLabelForm("  Estoque Inicial:"));
        painelForm.add(txtEstoque);
        painelForm.add(criarLabelForm("  Tipo de Regra:"));
        painelForm.add(chkIsLiteratura);

        JButton btnSalvarLivro = criarBotaoModerno("CADASTRAR LIVRO");
        painelForm.add(new JLabel());
        painelForm.add(btnSalvarLivro);

        txtListaLivros = new JTextArea();
        estilizarAreaLista(txtListaLivros);
        atualizarListaDeLivros();

        btnSalvarLivro.addActionListener(e -> {
            try {
                String titulo = txtTitulo.getText();
                String editora = txtEditora.getText();
                int ano = Integer.parseInt(txtAno.getText());
                int paginas = Integer.parseInt(txtPaginas.getText());
                int estoque = Integer.parseInt(txtEstoque.getText());
                int id = gerenciador.gerarProximoIdLivro();

                Livro novoLivro;
                if (chkIsLiteratura.isSelected()) {
                    novoLivro = new LivroLiteratura(id, titulo, editora, ano, paginas, estoque);
                } else {
                    novoLivro = new LivroTecnico(id, titulo, editora, ano, paginas, estoque);
                }

                gerenciador.cadastrarLivro(novoLivro);
                JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
                atualizarListaDeLivros();

                txtTitulo.setText("");
                txtEditora.setText("");
                txtAno.setText("");
                txtPaginas.setText("");
                txtEstoque.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preencha os campos numéricos corretamente!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        painelPrincipal.add(painelForm, BorderLayout.NORTH);
        
        JScrollPane scroll = new JScrollPane(txtListaLivros);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(50, 60, 85)));
        painelPrincipal.add(scroll, BorderLayout.CENTER);
        
        abas.addTab("Livros", painelPrincipal);
    }

    // ==========================================
    // ABA 3: LOCAÇÕES (DARK MODE)
    // ==========================================
    private void configurarAbaLocacoes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBackground(COR_FUNDO_DARK);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JPanel painelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        painelForm.setBackground(COR_CARD_DARK);
        painelForm.setBorder(criarBordaPainel(" Registrar Nova Locação "));

        JTextField txtIdCliente = new JTextField();
        JTextField txtIdLivro = new JTextField();
        JTextField txtDias = new JTextField("7");

        estilizarCampo(txtIdCliente);
        estilizarCampo(txtIdLivro);
        estilizarCampo(txtDias);

        painelForm.add(criarLabelForm("  ID do Cliente:"));
        painelForm.add(txtIdCliente);
        painelForm.add(criarLabelForm("  ID do Livro:"));
        painelForm.add(txtIdLivro);
        painelForm.add(criarLabelForm("  Quantidade de Dias:"));
        painelForm.add(txtDias);

        JButton btnLocar = criarBotaoModerno("REGISTRAR LOCAÇÃO");
        painelForm.add(new JLabel());
        painelForm.add(btnLocar);

        txtListaLocacoes = new JTextArea();
        estilizarAreaLista(txtListaLocacoes);
        atualizarListaDeLocacoes();

        btnLocar.addActionListener(e -> {
            try {
                int idCliente = Integer.parseInt(txtIdCliente.getText());
                int idLivro = Integer.parseInt(txtIdLivro.getText());
                int dias = Integer.parseInt(txtDias.getText());

                Cliente clienteSelecionado = null;
                for (Cliente c : gerenciador.listarClientes()) {
                    if (c.getId() == idCliente) {
                        clienteSelecionado = c;
                        break;
                    }
                }

                Livro livroSelecionado = null;
                for (Livro l : gerenciador.listarLivros()) {
                    if (l.getId() == idLivro) {
                        livroSelecionado = l;
                        break;
                    }
                }

                if (clienteSelecionado == null || livroSelecionado == null) {
                    JOptionPane.showMessageDialog(this, "Cliente ou Livro não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Cria o objeto de locação e tenta fazer a reserva controlada pelo boolean
                int idLocacao = gerenciador.gerarProximoIdLocacao();
                Locacao novaLocacao = new Locacao(idLocacao, clienteSelecionado, LocalDate.now(), dias);
                
                boolean reservouComSucesso = novaLocacao.adicionarLivro(livroSelecionado);

                // SE FALHAR A RESERVA: Mostra a caixinha de bloqueio que você precisava!
                if (!reservouComSucesso) {
                    JOptionPane.showMessageDialog(this, "Erro: Não foi possível reservar! O livro está esgotado ou já reservado por outro cliente.", "Bloqueio de Estoque", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Se passou da verificação, registra o resto com sucesso
                double valorPorDia = (livroSelecionado instanceof LivroLiteratura) ? 5.0 : 8.0; 
                double valorTotal = dias * valorPorDia;

                gerenciador.registrarNovaLocacao(novaLocacao);

                JOptionPane.showMessageDialog(this, "Locação registrada como Reserva!\n\n" +
                        "Cliente: " + clienteSelecionado.getNome() + "\n" +
                        "Livro: " + livroSelecionado.getTitulo() + "\n" +
                        "Prazo: " + dias + " dias\n" +
                        "Valor Total: R$ " + String.format("%.2f", valorTotal), "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                atualizarListaDeLocacoes();
                atualizarListaDeLivros();

                txtIdCliente.setText("");
                txtIdLivro.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Insira IDs e quantidade de dias válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        painelPrincipal.add(painelForm, BorderLayout.NORTH);
        
        JScrollPane scroll = new JScrollPane(txtListaLocacoes);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(50, 60, 85)));
        painelPrincipal.add(scroll, BorderLayout.CENTER);
        
        abas.addTab("Locações", painelPrincipal);
    }

    // ==========================================
    // MÉTODOS DE ATUALIZAÇÃO DOS OUTPUTS
    // ==========================================
    private void atualizarListaDeClientes() {
        StringBuilder sb = new StringBuilder("=== CLIENTES CADASTRADOS NO SISTEMA ===\n\n");
        for (Cliente c : gerenciador.listarClientes()) {
            sb.append(String.format("ID: %02d | Nome: %-25s | CPF: %-14s | Email: %s\n", 
                    c.getId(), c.getNome(), c.getCpf(), c.getEmail()));
        }
        txtListaClientes.setText(sb.toString());
    }

    private void atualizarListaDeLivros() {
        StringBuilder sb = new StringBuilder("=== ACERVO DE LIVROS DISPONÍVEIS ===\n\n");
        for (Livro l : gerenciador.listarLivros()) {
            String tipo = (l instanceof LivroLiteratura) ? "Literatura" : "Técnico   ";
            // Alterado para mostrar a quantidade disponível real da reserva
            sb.append(String.format("ID: %02d | Tipo: %s | Título: %-30s | Disponível: %d un (Total Físico: %d)\n", 
                    l.getId(), tipo, l.getTitulo(), l.getEstoqueDisponivel(), l.getEstoque()));
        }
        txtListaLivros.setText(sb.toString());
    }

    private void atualizarListaDeLocacoes() {
        StringBuilder sb = new StringBuilder("=== HISTÓRICO ATIVO DE LOCAÇÕES ===\n\n");
        for (Locacao loc : gerenciador.listarLocacoes()) {
            sb.append(String.format("ID Transação: %02d | Cliente Associado: %-25s\n", 
                    loc.getId(), loc.getCliente().getNome()));
        }
        txtListaLocacoes.setText(sb.toString());
    }
}