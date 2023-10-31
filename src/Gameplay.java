package src;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener {
    private JLabel imagemFundo;  // Criação do JLabel para a imagem de fundo
    private Player player;  // Criação do player
    private AlienFraco alienFraco;  // Criação do alien fraco

    public Gameplay() {
        setLayout(null);  // Layout do JPanel nulo

        // Criação do JLabel para a imagem de fundo
        imagemFundo = new JLabel();
        imagemFundo.setIcon(new ImageIcon("C:\\Users\\mathe\\Downloads\\SpaceInvaders\\assets\\Fundo.png"));
        add(imagemFundo);
        imagemFundo.setBounds(0, 0, 1920, 1080);

        // Criação do player
        player = new Player(100);
        add(player);
        player.setSize(60, 30);
        player.setLocation(950, 950);
        setComponentZOrder(player, 0);

        // Criação do alien fraco
        alienFraco = new AlienFraco(10);
        add(alienFraco);
        alienFraco.setSize(40, 32);
        alienFraco.setLocation(950, 0);
        setComponentZOrder(alienFraco, 0);

        addKeyListener(this); // Adiciona o KeyListener ao JPanel para capturar os eventos de teclado
        setFocusable(true); 
        new Timer(10, checaColisao).start();
    }

    // Método para capturar os eventos de teclado (seta esquerda, seta direita e espaço)
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_SPACE:
                player.atirar(10, 30, 500);
                break;
            case KeyEvent.VK_LEFT:
                player.moverEsquerda();
                break;
            case KeyEvent.VK_RIGHT:
                player.moverDireita();
                break;
        }
    }

    ActionListener checaColisao = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            for (Component componente : getComponents()) {
                if (componente instanceof Disparo) {
                    if(Arrays.asList(getComponents()).contains(alienFraco) && ((Disparo) componente).seColidiu(alienFraco)) {
                        alienFraco.setVida(alienFraco.getVida() - ((Disparo) componente).getDano());
                        remove(componente);
                        if(alienFraco.estaMorto()) remove(alienFraco);
                        revalidate();
                        repaint();
                    }
                } 
            }
        }
    };

    // Métodos não utilizados, mas necessários para poder implementar o KeyListener
    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}
}
