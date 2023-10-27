package src;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

// Classe principal que contém a janela inicial para iniciar o jogo
public class SpaceInvaders extends JFrame {
    // Botão para iniciar o jogo
    private JButton iniciar;
    ImageIcon imagemFundo = new ImageIcon("assets/Fundo.png");

    // Criação do JFrame
    public SpaceInvaders() {
        setTitle("Space Invaders");  // Título do Frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Comportamento da opção de fechar
        setSize(1920, 1080);  // Tamanho da janela (full screen)
        setLayout(new GridBagLayout());  // Criando o Frame através do layout GridBagLayout da biblioteca awt

        // Criando um objeto gbc da classe GridBagConstraints para definir as margens internas do objeto
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel labelFundo = new JLabel(imagemFundo);
        add(labelFundo);

        iniciar = new JButton("Iniciar");
        add(iniciar);
    
        setVisible(true);
    }

    public static void main(String[] args) {
        new SpaceInvaders();
    }
}
