package src;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

// Classe principal que contém a janela inicial para iniciar o jogo
public class SpaceInvaders extends JFrame {
    private JButton iniciar;  // Botão para iniciar o jogo
    private Image logo;  // Criação dos JLabels para a imagem de fundo e logo
    private Font space_invaders;  // Fonte do jogo
    private Sons sons;
    private Fundo fundo;

    // Criação do JFrame
    public SpaceInvaders() {
        sons = new Sons();
        fundo = new Fundo();
        try {
            File file = new File("assets\\Logo.png");
            logo = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTitle("Space Invaders");  // Título da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Ação ao fechar a janela
        getContentPane().setLayout(null);  // Layout da janela nulo
        setSize(1920, 1080);  // Tamanho da janela
     
        // Carregamento da fonte
        try {
            space_invaders = Font.createFont(Font.TRUETYPE_FONT, new File("fonts\\humanoid.regular.ttf")).deriveFont(40f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts\\humanoid.regular.ttf")));
        } catch (FontFormatException | IOException e) {
            System.out.println("Erro ao carregar fontes!");
        } catch (Exception e) {
            System.out.println("Erro inesperado!");;
        }

        int frameWidth = getWidth();
        int frameHeight = getHeight();

        int logoWidth = logo.getWidth(null);
        int logoHeight = logo.getHeight(null);

        int logoX = (frameWidth - logoWidth) / 2;
        int logoY = (frameHeight - logoHeight) / 2;
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                fundo.draw(g);

                g.drawImage(logo, logoX, logoY, logoWidth, logoHeight, null);
            }
        };

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Criação do botão iniciar
        iniciar = new JButton("INICIAR");
        iniciar.setFont(space_invaders);
        iniciar.setForeground(Color.RED);
        iniciar.setBackground(Color.YELLOW);

        int buttonWidth = 300;
        int buttonHeight = 30;
        int buttonX = (frameWidth - buttonWidth) / 2;
        int buttonY = logoY + logoHeight + 50; 
        iniciar.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        iniciar.setHorizontalAlignment(SwingConstants.CENTER);
        iniciar.setFocusPainted(false); 
        getContentPane().add(iniciar);
        iniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarJogo();
            }
        });
    
        // Configurações da janela em fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        sons.tocarMusica("sounds/spaceinvaders1.wav");
    }

    // Método para iniciar o jogo
    public void iniciarJogo() {
        JFrame janela = new JFrame("Space Invaders");
        janela.setTitle("Space Invaders");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(1920, 1080);
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
        janela.setUndecorated(true);
        janela.add(new Gameplay());
        janela.setVisible(true);
        setVisible(false);
        sons.tocarMusica("sounds/spaceinvaders1.wav");
    }
    public static void main(String[] args) {
        new SpaceInvaders();  // Criação do objeto SpaceInvaders (inicia o jogo)
    }
}