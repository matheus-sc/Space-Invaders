package src;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SpaceInvaders extends JFrame {
    private JButton iniciar;  // JButton para poder iniciar o jogo
    private JLabel highScoreLabel;  // JLabel para poder mostrar o highscore
    private JComboBox<String> dificuldade;  // JComboBox para poder escolher a dificuldade
    private Image logo;  // Imagem do logo do jogo
    private Font space_invaders;  // Fonte do jogo
    private Sons sons;  // Sons do jogo
    private Fundo fundo;  // Fundo do jogo

    public SpaceInvaders() {
        // Inicia a música na tela inicial
        sons = Sons.getInstance();
        sons.tocarMusica("sounds/spaceinvaders1.wav");
        
        fundo = new Fundo();  // Inicia o fundo

        // Inicia o JComboBox e o posiciona
        try {
            File fileLogo = new File("assets/Logo.png");
            logo = ImageIO.read(fileLogo);
        } catch (IOException e) {
            System.out.println("Erro ao carregar logo!");
        } catch (Exception e) {
            System.out.println("Erro inesperado!");
        }

        // Carregamento da fonte
        try {
            space_invaders = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/SpaceFont.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/SpaceFont.ttf")));
        } catch (FontFormatException | IOException e) {
            System.out.println("Erro ao carregar fontes!");
        } catch (Exception e) {
            System.out.println("Erro inesperado!");
        }

        // Faz o comportamento da janela (título, ação ao fechar e layout)
        setTitle("Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Obtém o tamanho da tela em tempo de execução
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        // Define o tamanho da janela de acordo com a tela
        setSize(screenSize);
        setLocationRelativeTo(null);

        // Cria um JPanel para poder adicionar os componentes
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                fundo.draw(g);

                // Redimensione o logotipo de acordo com o tamanho da tela
                int logoWidth = (int) (screenSize.getWidth() * 0.6);
                int logoHeight = (int) (logoWidth * logo.getHeight(null) / logo.getWidth(null));
                int logoX = (screenSize.width - logoWidth) / 2;
                int logoY = (screenSize.height - logoHeight) / 2;
                g.drawImage(logo, logoX, logoY, logoWidth, logoHeight, null);

                // Redimensione o botão de acordo com o tamanho da tela
                int buttonWidth = 300;
                int buttonHeight = 30;
                int buttonX = (screenSize.width - buttonWidth) / 2;
                int buttonY = logoY + logoHeight + 50;
                iniciar.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

                highScoreLabel.setBounds(10, 10, 600, 30);

                // Redimensione o JComboBox de acordo com o tamanho da tela
                int comboBoxWidth = 200;
                int comboBoxHeight = 50;
                int comboBoxX = (screenSize.width - comboBoxWidth) / 2;
                int comboBoxY = buttonY + buttonHeight + 50;
                dificuldade.setBounds(comboBoxX, comboBoxY, comboBoxWidth, comboBoxHeight);

            }
        };

        // Define o JPanel como o contentPane
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Inicia o JComboBox e o adiciona ao JPanel
        dificuldade = new JComboBox<>();
        dificuldade.addItem("Fácil");
        dificuldade.addItem("Médio");
        dificuldade.addItem("Difícil");
        dificuldade.setFont(space_invaders);
        dificuldade.setForeground(Color.RED);
        dificuldade.setBackground(Color.YELLOW);
        dificuldade.setFocusable(false);
        contentPane.add(dificuldade);

        // Inicia o JButton e o adiciona ao JPanel
        iniciar = new JButton("INICIAR");
        iniciar.setFont(space_invaders);
        iniciar.setForeground(Color.RED);
        iniciar.setBackground(Color.YELLOW);
        iniciar.setFocusPainted(false);
        contentPane.add(iniciar);

        // Inicia o highscore e o adiciona ao JPanel
        highScoreLabel = new JLabel();
        highScoreLabel.setFont(space_invaders);
        highScoreLabel.setForeground(Color.RED);
        highScoreLabel.setBackground(Color.YELLOW);
        highScoreLabel.setFocusable(false);
        contentPane.add(highScoreLabel);

        // Obtém o highscore do arquivo highscore.txt. Se não existir, o highscore é 0
        try {
            File fileScore = new File("highscore.txt");
            if (fileScore.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(fileScore));
                String highScore = reader.readLine();
                reader.close();

                if (highScore != null) {
                    highScoreLabel.setText("High Score: " + highScore);
                }
            } else {
                highScoreLabel.setText("High Score: 0");
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de pontuação!");
        }

        // Adiciona um ActionListener ao JButton com o método iniciarJogo()
        iniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarJogo();
            }
        });

        // Define a janela como maximizada e sem bordas
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);

        sons.tocarMusica("sounds/spaceinvaders1.wav"); // Toca a música
    }

    // Método para iniciar o jogo, criando um novo Gameplay e definindo-o como o contentPane
    public void iniciarJogo() {
        String dificuldade = this.dificuldade.getSelectedItem().toString();
        Gameplay gameplay = new Gameplay(dificuldade);
        setContentPane(gameplay);
        gameplay.setVisible(true);
        gameplay.requestFocusInWindow();
        sons.tocarMusica("sounds/spaceinvaders1.wav");
    }

    // Main para iniciar o jogo
    public static void main(String[] args) {
        new SpaceInvaders();
    }
}