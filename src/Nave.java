package src;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// Interface para as naves do jogo (player e aliens)
public abstract class Nave {
    private int vida, x, y;
    private Image sprite;
    private boolean podeAtirar = true;  // Variável para controlar a taxa de disparo do player
    private Fundo fundo = new Fundo();

    public void draw(Graphics g) {
        g.drawImage(getSprite(), x, y, null);
    }
    
    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVida() {
        return vida;
    }

    public void setSprite(String caminho) {
        try {
            this.sprite = ImageIO.read(new File(caminho));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getSprite() {
        return sprite;
    }

    public boolean getPodeAtirar() {
        return podeAtirar;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return sprite.getWidth(null);
    }

    public int getHeight() {
        return sprite.getHeight(null);
    }

    public int getFundoWidth() {
        return fundo.getWidth();
    }

    public boolean estaMorto() {
        if (vida <= 0) {
            return true;
        } return false;
    }

    public abstract void atirar(int dano, int velocidade, int cooldown);

    public abstract void moverEsquerda();

    public abstract void moverDireita();
}
