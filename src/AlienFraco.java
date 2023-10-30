package src;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class AlienFraco extends JLabel implements Nave {
    private int vida;
    private ImageIcon sprite;

    public AlienFraco(int vida) {
        this.vida = vida;

        sprite = new ImageIcon("C:\\Users\\mathe\\Downloads\\SpaceInvaders\\assets\\AlienFraco.png");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sprite.getImage(), 0, 0, null);
    }

    public int getX() {
        return super.getX();
    }

    public int getY() {
        return super.getY();
    }

    public int getWidth() {
        return super.getWidth();
    }

    public int getHeight() {
        return super.getHeight();
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

    public void atirar(int dano, int velocidade, int cooldown) {

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
