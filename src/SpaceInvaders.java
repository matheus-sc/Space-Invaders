package src;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
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
import java.io.File;
import java.io.IOException;

public class SpaceInvaders extends JFrame {
    private JButton iniciar;
    private Image logo;
    private Font space_invaders;
    private Sons sons;
    private Fundo fundo;

    public SpaceInvaders() {
        sons = new Sons();
        fundo = new Fundo();
        try {
            File file = new File("assets/Logo.png");
            logo = ImageIO.read(file);
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
            space_invaders = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/humanoid.regular.ttf")).deriveFont(40f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/humanoid.regular.ttf")));
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
            }
        };

        setContentPane(contentPane);
        contentPane.setLayout(null);

        iniciar = new JButton("INICIAR");
        iniciar.setFont(space_invaders);
        iniciar.setForeground(Color.RED);
        iniciar.setBackground(Color.YELLOW);

        contentPane.add(iniciar);

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
        JFrame janela = new JFrame("Space Invaders");
        janela.setTitle("Space Invaders");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(getSize());
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
        janela.setUndecorated(true);
        janela.add(new Gameplay());
        janela.setVisible(true);
        setVisible(false);
        sons.tocarMusica("sounds/spaceinvaders1.wav");
    }

    public static void main(String[] args) {
        new SpaceInvaders();
    }
}