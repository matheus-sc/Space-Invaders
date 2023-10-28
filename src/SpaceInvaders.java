package src;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

// Classe principal que contém a janela inicial para iniciar o jogo
public class SpaceInvaders extends JFrame {
    // Botão para iniciar o jogo
    private JButton iniciar;
    private JLabel imagemFundo = new JLabel(), logo = new JLabel();

    // Criação do JFrame
    public SpaceInvaders() {
        setTitle("Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setSize(1920, 1080);
     
        /*
        try {
            File fontFile = new File("C:\\Users\\mathe\\Downloads\\SpaceInvaders\\fonts\\space_invaders.ttf");
            Font space_invaders = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(space_invaders);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        
        iniciar = new JButton("INICIAR");
        iniciar.setFont(new Font("sansSerif", 1, 30));
        iniciar.setForeground(Color.RED);
        iniciar.setBackground(Color.YELLOW);
        iniciar.setBounds(820, 740, 300, 30);
        iniciar.setHorizontalAlignment(SwingConstants.CENTER);
        iniciar.setFocusPainted(false); 
        getContentPane().add(iniciar);
        

        logo.setIcon(new javax.swing.ImageIcon("C:\\Users\\mathe\\Downloads\\SpaceInvaders\\assets\\Logo.png")); // NOI18N
        getContentPane().add(logo);
        logo.setBounds(500, 100, 1100, 530);

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
