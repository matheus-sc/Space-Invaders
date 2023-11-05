package src;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

// Classe para os disparos
public class Disparo {
    private int dano, velocidade, cooldown, x, y;  // Variáveis para o dano, velocidade, cooldown e posição do disparo
    private Image sprite;  // Variável para o sprite do disparo

    public Disparo(int dano, int velocidade, int cooldown, String spritePath, Nave atirador) {
        // Inicializa os atributos
        this.dano = dano;
        this.velocidade = velocidade;
        this.cooldown = cooldown;

        // Obtém a posição do atirador para centralizar o disparo de acordo
        x = atirador.getX() + atirador.getWidth() / 2 - 4;
        y = atirador.getY() + atirador.getHeight() / 2 - 30;

        // Carrega o sprite do disparo
        try {
            sprite = ImageIO.read(getClass().getResource(spritePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sets e gets
    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return sprite.getWidth(null);
    }

    public int getHeight() {
        return sprite.getHeight(null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    // Método para desenhar o disparo na tela
    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, null);
    }

    // Método para verificar se o disparo do player colidiu com algum inimigo
    public boolean seColidiu(ArrayList<Alien> inimigos) {
        Rectangle disparo = new Rectangle(getX(), getY(), getWidth(), getHeight());  // Cria um retângulo para o disparo
        for (Nave inimigo : inimigos) {
            Rectangle hitboxInimigo = new Rectangle(inimigo.getX(), inimigo.getY(), inimigo.getWidth(), inimigo.getHeight()); // Cria um retângulo para o inimigo
            if (disparo.intersects(hitboxInimigo)) {
                inimigo.setVida(inimigo.getVida() - getDano());  // Diminui a vida do inimigo caso o disparo o atinja
                return true;
            }
        }
        if (getY() <= 0) {
            return true;  // Retorna true caso o disparo saia da tela
        }
        return false;  // Retorna false caso o disparo não colida com nenhum inimigo
    }

    // Método para verificar se o disparo de um inimigo colidiu com o player
    public boolean seColidiu(Player player) {
        Rectangle disparo = new Rectangle(getX(), getY(), getWidth(), getHeight());  // Cria um retângulo para o disparo
        Rectangle hitboxPlayer = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());  // Cria um retângulo para o player
        if (disparo.intersects(hitboxPlayer)) {
            player.setVida(player.getVida() - getDano());  // Diminui a vida do player caso o disparo o atinja
            return true;
        }
        if (getY() >= Toolkit.getDefaultToolkit().getScreenSize().getHeight()) {
            return true;  // Retorna true caso o disparo saia da tela
        }
        return false;  // Retorna false caso o disparo não colida com o player
    }

    // Métodos de movimentação
    public void moverCima() {
        y -= velocidade;
    }

    public void moverBaixo() {
        y += velocidade;
    }
}
