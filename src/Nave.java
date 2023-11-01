package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

// Interface para as naves do jogo (player e aliens)
public abstract class Nave {
    private int vida, x, y;
    private Image sprite;
    private boolean podeAtirar = true;  // Variável para controlar a taxa de disparo do player
    private Fundo fundo = new Fundo();
    private ArrayList<Disparo> disparos = new ArrayList<>();  // Criação do ArrayList para os disparos do player

    public void draw(Graphics g) {
        g.drawImage(getSprite(), x, y, null);

        g.setColor(Color.RED);
        g.drawRect(x, y, getWidth() - 1, getHeight() - 1);
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

    public void setPodeAtirar(boolean podeAtirar) {
        this.podeAtirar = podeAtirar;
    }

    public void setDisparos(Disparo disparo) {
        disparos.add(disparo);
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

    public ArrayList<Disparo> getDisparos() {
        return disparos;
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

    public int getFundoHeight() {
        return fundo.getHeight();
    }

    public boolean estaMorto() {
        if (vida <= 0) {
            return true;
        } return false;
    }

    public abstract boolean atirar(int dano, int velocidade, int cooldown);

    public abstract void moverEsquerda();

    public abstract void moverDireita();
}
