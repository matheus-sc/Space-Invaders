package src;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener {
    private JLabel imagemFundo;  // Criação do JLabel para a imagem de fundo
    private Player player;  // Criação do player
    private ArrayList<AlienFraco> aliensFracos = new ArrayList<>();  // Criação do alien fraco

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
        final int initialX = 950;
        final int initialY = 500;
        final int spacing = 30;
        for (int i = 0; i < 10; i++) {
            AlienFraco alien = new AlienFraco(10, 10);
            add(alien);
            alien.setSize(alien.getLargura(), alien.getAltura());
            alien.setLocation(initialX + i * (alien.getLargura() + spacing), initialY);
            setComponentZOrder(alien, 0);
            aliensFracos.add(alien);
        }

        addKeyListener(this); // Adiciona o KeyListener ao JPanel para capturar os eventos de teclado
        setFocusable(true); 
        new Timer(10, checaColisao).start();
        new Timer(100, movimentoAliens).start();
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
                    for (AlienFraco alien : aliensFracos) {
                        if(((Disparo) componente).seColidiu(alien)) {
                            alien.setVida(alien.getVida() - ((Disparo) componente).getDano());
                            remove(componente);
                            if(alien.estaMorto()) {
                                remove(alien);
                                aliensFracos.remove(alien);
                            }
                            revalidate();
                            repaint();
                            break; // Sai do loop interno se uma colisão for detectada
                        }
                    }
                } 
            }
        }
    };

    boolean direcao = false;
    ActionListener movimentoAliens = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            // First move all aliens
            for (AlienFraco alienFraco : aliensFracos) {
                if (!direcao) {
                    alienFraco.moverEsquerda();
                } else {
                    alienFraco.moverDireita();
                }
            }
    
            // Then check if any alien has hit the edge and update direcao
            for (AlienFraco alienFraco : aliensFracos) {
                if (alienFraco.getX() <= 0) {
                    direcao = true;
                    break;
                } else if (alienFraco.getX() >= getParent().getWidth() - alienFraco.getWidth()) {
                    direcao = false;
                    break;
                }
            }
        }
    };

    // Métodos não utilizados, mas necessários para poder implementar o KeyListener
    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}
}
