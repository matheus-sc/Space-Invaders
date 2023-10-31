package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Disparo extends JLabel{
    private int dano, velocidade, cooldown;
    private ImageIcon sprite;

    public Disparo(int dano, int velocidade, int cooldown, String spritePath) {
        this.dano = dano;
        this.velocidade = velocidade;
        this.cooldown = cooldown;

        sprite = new ImageIcon(spritePath);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sprite.getImage(), 0, 0, null);

        g.setColor(Color.RED);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    public int getDano() {
        return dano;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public int getCooldown() {
        return cooldown;
    }

    public boolean seColidiu(AlienFraco inimigos) {
        Rectangle disparo = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle inimigo = new Rectangle(inimigos.getX(), inimigos.getY(), inimigos.getWidth(), inimigos.getHeight());
        if (disparo.intersects(inimigo)) {
            return true;
        }
        return false;
    }
}
