package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Player extends JLabel implements Nave {
    private int vida;  // Vida do player
    private ImageIcon sprite;  // Sprite do player
    private boolean podeAtirar = true;  // Variável para controlar a taxa de disparo do player

    public Player(int vida) {
        this.vida = vida;  // Inicializa a vida do player

        sprite = new ImageIcon("C:\\Users\\mathe\\Downloads\\SpaceInvaders\\assets\\Jogador.png");  // Inicializa o sprite do player
    }

    // Método para desenhar o sprite do player
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sprite.getImage(), 0, 0, null);

        g.setColor(Color.ORANGE);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    // Métodos da interface Nave para retornar a posição e o tamanho do player
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

    // Métodos da interface Nave para controlar a vida do player
    public void setVida(int vida) {
        this.vida = vida;
    }
    public int getVida() {
        return vida;
    }
    public boolean estaMorto() {
        return true;
    }

    // Método da interface Nave para controlar o disparo do player
    public void atirar(int dano, int velocidade, int cooldown) {
        if (!podeAtirar) return;  // Checa se o player pode atirar

        // Inicializa a posição inicial do disparo
        int x = (getX() + getWidth() / 2) - 3;
        int y = (getY() - getHeight() / 2) - 10;
    
        // Cria um novo objeto Disparo
        final String spritePath = "C:\\Users\\mathe\\Downloads\\SpaceInvaders\\assets\\TiroPlayer.png";
        Disparo disparo = new Disparo(dano, velocidade, cooldown, spritePath);
        
        getParent().add(disparo);  // Adiciona o disparo ao JPanel
        // Define a posição e o tamanho do disparo
        disparo.setSize(10, 20);
        disparo.setLocation(x, y);
        getParent().setComponentZOrder(disparo, 0);
    
        // Inicializa um Timer para controlar o movimento do disparo
        int delay = 50; // delay para o Timer
        Timer timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Move o disparo para cima
                int newY = disparo.getY() - disparo.getVelocidade();
                disparo.setLocation(x, newY);
                disparo.repaint();
    
                System.out.println("Posição Y do disparo: " + disparo.getY());
                System.out.println("Tamanho da janela: " + getParent().getHeight());
                // Checa se o disparo chegou ao topo da tela
                if (disparo.getY() < 0) {
                    getParent().remove(disparo);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();  // Inicia o Timer

        podeAtirar = false; // Coloca a variável podeAtirar como false para impedir que o player atire novamente
        // Inicializa um Timer para controlar o cooldown do disparo
        Timer timerCooldown = new Timer(disparo.getCooldown(), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                podeAtirar = true; // Coloca a variável podeAtirar como true para permitir que o player atire novamente
                ((Timer) e.getSource()).stop();
            }
        });
        timerCooldown.start();  // Inicia o Timer
    }

    // Métodos da interface Nave para controlar o movimento do player
    public void moverEsquerda() {
        int x = getX();
        if (x > 0) setLocation(x - 10, getY());
    }
    public void moverDireita() {
        int x = getX();
        if (x < getParent().getWidth() - getWidth()) setLocation(x + 10, getY());
    }
}
