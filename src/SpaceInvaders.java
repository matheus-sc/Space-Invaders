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
    private JButton iniciar;
    private JLabel highScoreLabel;
    private Image logo;
    private Font space_invaders;
    private Sons sons;
    private Fundo fundo;
    private JComboBox<String> dificuldade;

    public SpaceInvaders() {
        sons = Sons.getInstance();
        sons.tocarMusica("sounds/spaceinvaders1.wav");
        fundo = new Fundo();

        highScoreLabel = new JLabel();
        highScoreLabel.setBounds(10, 10, 200, 30); // Adjust the position and size as needed
        getContentPane().add(highScoreLabel);

        try {
            File fileLogo = new File("assets/Logo.png");
            logo = ImageIO.read(fileLogo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTitle("Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Obtém o tamanho da tela em tempo de execução
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        // Define o tamanho da janela de acordo com a tela
        setSize(screenSize);
        setLocationRelativeTo(null);

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

                int buttonWidth = 300;
                int buttonHeight = 30;
                int buttonX = (screenSize.width - buttonWidth) / 2;
                int buttonY = logoY + logoHeight + 50;
                iniciar.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

                highScoreLabel.setBounds(10, 10, 600, 30); // Adjust the position and size as needed

                int comboBoxWidth = 200;
                int comboBoxHeight = 50;
                int comboBoxX = (screenSize.width - comboBoxWidth) / 2;
                int comboBoxY = buttonY + buttonHeight + 50;
                dificuldade.setBounds(comboBoxX, comboBoxY, comboBoxWidth, comboBoxHeight);

            }
        };

        setContentPane(contentPane);
        contentPane.setLayout(null);

        iniciar = new JButton("INICIAR");
        iniciar.setFont(space_invaders);
        iniciar.setForeground(Color.RED);
        iniciar.setBackground(Color.YELLOW);
        iniciar.setFocusPainted(false);
        contentPane.add(iniciar);

        highScoreLabel = new JLabel();
        highScoreLabel.setFont(space_invaders);
        highScoreLabel.setForeground(Color.RED);
        highScoreLabel.setBackground(Color.YELLOW);
        highScoreLabel.setFocusable(false);
        contentPane.add(highScoreLabel);

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
            e.printStackTrace();
        }

        dificuldade = new JComboBox<>();
        dificuldade.addItem("Fácil");
        dificuldade.addItem("Médio");
        dificuldade.addItem("Difícil");
        dificuldade.setFont(space_invaders);
        dificuldade.setForeground(Color.RED);
        dificuldade.setBackground(Color.YELLOW);
        dificuldade.setFocusable(false);
        contentPane.add(dificuldade);

        iniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarJogo();
            }
        });

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        sons.tocarMusica("sounds/spaceinvaders1.wav");
    }

    public void iniciarJogo() {
        String dificuldade = this.dificuldade.getSelectedItem().toString();
        Gameplay gameplay = new Gameplay(dificuldade);
        setContentPane(gameplay);
        gameplay.setVisible(true);
        gameplay.requestFocusInWindow();
        sons.tocarMusica("sounds/spaceinvaders1.wav");
    }

    public static void main(String[] args) {
        new SpaceInvaders();
    }
}