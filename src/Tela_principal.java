import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tela_principal extends JFrame {
    public Tela_principal() {
        setTitle("Sistema de Gestão");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 550));
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout(10, 10));

        // Criação dos botões do menu 
        JButton btn_cadastro = new JButton("Cadastrar");
        JButton btn_editar = new JButton("Edição");
        JButton btn_saida = new JButton("Saída");
        JButton btn_entrada = new JButton("Entrada");
        JButton btn_relatorio = new JButton("Relatórios");

        // Configuração do painel de menu
        JPanel menu = new JPanel(); 
        menu.setBackground(Color.decode("#44474d"));
        menu.setPreferredSize(new Dimension(150, 550)); 
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        // Configuração de tamanho dos botões
        Dimension btnSize = new Dimension(120, 35);
        btn_cadastro.setMaximumSize(btnSize);
        btn_cadastro.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_editar.setMaximumSize(btnSize);
        btn_editar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_saida.setMaximumSize(btnSize);
        btn_saida.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_entrada.setMaximumSize(btnSize);
        btn_entrada.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_relatorio.setMaximumSize(btnSize);
        btn_relatorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Adicionando componentes ao menu
        menu.add(Box.createVerticalStrut(20));
        menu.add(btn_cadastro);
        menu.add(Box.createRigidArea(new Dimension(3, 15)));
        menu.add(btn_editar);
        menu.add(Box.createRigidArea(new Dimension(3, 15)));
        menu.add(btn_saida);
        menu.add(Box.createRigidArea(new Dimension(3, 15)));
        menu.add(btn_entrada);
        menu.add(Box.createRigidArea(new Dimension(3, 15)));
        menu.add(btn_relatorio);
        menu.add(Box.createVerticalGlue());
        add(menu, BorderLayout.WEST);

        // Criação dos componentes da tela cadastrar
        JTextField nome_produto_cadastrar = new JTextField(25);
        nome_produto_cadastrar.setMargin(new Insets(8, 8, 8, 8));
        nome_produto_cadastrar.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel nome_produto_cadastrar_label = new JLabel("Nome do Produto:");
        nome_produto_cadastrar_label.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField preco_cadastrar = new JTextField(25);
        preco_cadastrar.setMargin(new Insets(8, 8, 8, 8));
        preco_cadastrar.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel preco_cadastrar_label = new JLabel("Preço:");
        preco_cadastrar_label.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Configuração do campo de descrição
        JTextArea descricao_cadastro = new JTextArea(8, 30);
        descricao_cadastro.setLineWrap(true);
        descricao_cadastro.setWrapStyleWord(true);
        descricao_cadastro.setMargin(new Insets(8, 8, 8, 8));
        descricao_cadastro.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Placeholder para o campo de descrição
        descricao_cadastro.setText("Digite a descrição do produto aqui...");
        descricao_cadastro.setForeground(Color.GRAY);
        descricao_cadastro.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if(descricao_cadastro.getText().equals("Digite a descrição do produto aqui...")) {
                    descricao_cadastro.setText("");
                    descricao_cadastro.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if(descricao_cadastro.getText().isEmpty()) {
                    descricao_cadastro.setForeground(Color.GRAY);
                    descricao_cadastro.setText("Digite a descrição do produto aqui...");
                }
            }
        });

        JScrollPane descricaoScroll = new JScrollPane(descricao_cadastro);
        descricaoScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        descricaoScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        descricaoScroll.setPreferredSize(new Dimension(450, 250));
        descricaoScroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        JLabel descricaoLabel = new JLabel("Descrição:");
        descricaoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Configuração dos botões
        JButton btn_cadastrar_produto = new JButton("Salvar cadastro");
        btn_cadastrar_produto.setPreferredSize(new Dimension(150, 35));
        btn_cadastrar_produto.setFont(new Font("Arial", Font.BOLD, 12));
        
        JButton btn_cancelar_cadastrar = new JButton("Cancelar");
        btn_cancelar_cadastrar.setPreferredSize(new Dimension(150, 35));
        btn_cancelar_cadastrar.setFont(new Font("Arial", Font.BOLD, 12));

        // Configuração do painel de cadastro com GridBagLayout
        JPanel tela_cadastrar = new JPanel(new GridBagLayout());
        tela_cadastrar.setBackground(Color.decode("#f0f0f0")); // Fundo mais claro
        tela_cadastrar.setPreferredSize(new Dimension(650, 550));
        tela_cadastrar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Linha 0 - Nome do Produto
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        tela_cadastrar.add(nome_produto_cadastrar_label, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tela_cadastrar.add(nome_produto_cadastrar, gbc);
        
        // Linha 1 - Preço
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        tela_cadastrar.add(preco_cadastrar_label, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tela_cadastrar.add(preco_cadastrar, gbc);
        
        // Linha 2 - Descrição
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        tela_cadastrar.add(descricaoLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;  // Peso máximo para expandir verticalmente
        gbc.fill = GridBagConstraints.BOTH;
        tela_cadastrar.add(descricaoScroll, gbc);
        
        // Linha 3 - Botões
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        tela_cadastrar.add(btn_cadastrar_produto, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        tela_cadastrar.add(btn_cancelar_cadastrar, gbc);
        

        add(tela_cadastrar, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Tela_principal();
        });
    }
}