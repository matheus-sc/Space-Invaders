package src;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Toolkit;

import javax.imageio.ImageIO;

// Classe abstrata para as naves do jogo (player e alien)
public abstract class Nave {
    private int vida, x, y;  // Variáveis para a vida e posição da nave
    private Image sprite;  // Variável para o sprite da nave
    private boolean podeAtirar = true; // Variável para controlar a taxa de disparo do player
    private ArrayList<Disparo> disparos = new ArrayList<>(); // ArrayList de disparos da nave

    // Método para desenhar a nave na tela
    public void draw(Graphics g) {
        int screenWidth = getScreenWidth();
        int screenHeight = getScreenHeight();

        int screenX = x * screenWidth / getScreenWidth();
        int screenY = y * screenHeight / getScreenHeight();
        int width = getWidth() * screenWidth / getScreenWidth();
        int height = getHeight() * screenHeight / getScreenHeight();

        g.drawImage(getSprite(), screenX, screenY, width, height, null);

    }

    // Sets e gets

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
            System.out.println("Erro ao carregar nave!");
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

    public int getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    public int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    // Método para verificar se a nave está morta
    public boolean estaMorto() {
        if (vida <= 0) {
            return true;
        }
        return false;
    }

    // Métodos abstratos de movimentação e ataque
    public abstract boolean atirar(int dano, int velocidade, int cooldown, String spriteTiroPath);

    public abstract void moverEsquerda();

    public abstract void moverDireita();
}