package src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener {
    private JLabel imagemFundo;
    private Player player;

    public Gameplay() {
        setLayout(null);

        imagemFundo = new JLabel();
        imagemFundo.setIcon(new ImageIcon("C:\\Users\\mathe\\Downloads\\SpaceInvaders\\assets\\Fundo.png"));
        add(imagemFundo);
        imagemFundo.setBounds(0, 0, 1920, 1080);

        player = new Player(100);
        add(player);
        player.setBounds(950, 950, 60, 30);
        setComponentZOrder(player, 0);

        addKeyListener(this);
        setFocusable(true);
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) player.moverEsquerda();
        else if (keyCode == KeyEvent.VK_RIGHT) player.moverDireita();
    }

    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}
}
