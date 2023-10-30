package src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener {
    private JLabel imagemFundo;
    private Player player;
    private AlienFraco alienFraco;

    public Gameplay() {
        setLayout(null);

        imagemFundo = new JLabel();
        imagemFundo.setIcon(new ImageIcon("C:\\Users\\mathe\\Downloads\\SpaceInvaders\\assets\\Fundo.png"));
        add(imagemFundo);
        imagemFundo.setBounds(0, 0, 1920, 1080);

        player = new Player(100);
        add(player);
        player.setSize(60, 30);
        player.setLocation(950, 950);
        setComponentZOrder(player, 0);

        alienFraco = new AlienFraco(30);
        add(alienFraco);
        alienFraco.setBounds(400, 400, 40, 32);
        setComponentZOrder(alienFraco, 0);

        addKeyListener(this);
        setFocusable(true);
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_SPACE:
                player.atirar(10, 10, 500);
                break;
            case KeyEvent.VK_LEFT:
                player.moverEsquerda();
                break;
            case KeyEvent.VK_RIGHT:
                player.moverDireita();
                break;
        }
    }

    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}
}
