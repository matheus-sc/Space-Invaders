package src;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Disparo extends JLabel{
    private int dano, velocidade, cooldown;
    private ImageIcon sprite;

    public Disparo(int dano, int velocidade, int cooldown) {
        this.dano = dano;
        this.velocidade = velocidade;
        this.cooldown = cooldown;

        sprite = new ImageIcon("C:\\Users\\mathe\\Downloads\\SpaceInvaders\\assets\\Tiro.png");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sprite.getImage(), 0, 0, null);
    }

    public int getVelocidade() {
        return velocidade;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void seColidiu(Nave nave) {
        Rectangle hitBoxDisparo = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle hitBoxNave = new Rectangle(nave.getX(), nave.getY(), nave.getWidth(), nave.getHeight());
        if (hitBoxDisparo.intersects(hitBoxNave)) nave.setVida(nave.getVida() - dano);
    }
}
