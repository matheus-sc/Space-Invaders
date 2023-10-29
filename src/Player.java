package src;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Player extends JLabel implements Nave {
    private int vida;
    private ImageIcon sprite;

    public Player(int vida) {
        this.vida = vida;

        sprite = new ImageIcon("C:\\Users\\mathe\\Downloads\\SpaceInvaders\\assets\\Jogador.png");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sprite.getImage(), 0, 0, null);
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVida() {
        return vida;
    }

    public boolean estaMorto() {
        return true;
    }

    public void atirar() {

    }

    public void moverEsquerda() {
        int x = getX();
        if (x > 0) setLocation(x - 10, getY());
    }

    public void moverDireita() {
        int x = getX();
        if (x < getParent().getWidth() - getWidth()) setLocation(x + 10, getY());
    }
}
