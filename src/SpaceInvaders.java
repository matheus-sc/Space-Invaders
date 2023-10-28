package src;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

// Classe principal que contém a janela inicial para iniciar o jogo
public class SpaceInvaders extends JFrame {
    // Botão para iniciar o jogo
    private JButton iniciar;
    private JLabel imagemFundo = new JLabel();

    // Criação do JFrame
    public SpaceInvaders() {
        setTitle("Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setSize(1920, 1080);
    
        iniciar = new JButton("Iniciar");
        // iniciar.addActionListener(new IniciarJogo(this));
        getContentPane().add(iniciar);
        iniciar.setSize(1, 50);
        iniciar.setBounds(850, 540, 260, 23);

        imagemFundo.setIcon(new ImageIcon("C:\\Users\\mathe\\Downloads\\SpaceInvaders\\assets\\Fundo.png"));
        getContentPane().add(imagemFundo);
        imagemFundo.setBounds(0, 0, 1920, 1080);
    
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SpaceInvaders().setVisible(true);
            }
        });
    }
}
