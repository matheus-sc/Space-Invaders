package src;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Player extends JLabel implements Nave {
    private int vida;
    private ImageIcon sprite;
    private boolean podeAtirar = true;

    public Player(int vida) {
        this.vida = vida;

        sprite = new ImageIcon("C:\\Users\\mathe\\Downloads\\SpaceInvaders\\assets\\Jogador.png");
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
        if (!podeAtirar) return;

        // Get the player's position
        int x = getX() - 45;
        int y = getY() - 85;
    
        // Create a new Disparo object
        Disparo disparo = new Disparo(dano, velocidade, cooldown);
        
        getParent().add(disparo);
        // Set the position of the Disparo object
        disparo.setSize(150, 150);
        disparo.setLocation(x, y);
        getParent().setComponentZOrder(disparo, 0);
        System.out.println("Disparo position: " + disparo.getX() + ", " + disparo.getY() + ", " + disparo.getWidth() + ", " + disparo.getHeight() + ", " + disparo.getVelocidade());
    
        // Start a Timer to move the Disparo object up the screen
        int delay = 50; // Update the position every 10 milliseconds
        Timer timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Move the Disparo object up the screen
                int newY = disparo.getY() - disparo.getVelocidade();
                disparo.setLocation(x, newY);
                disparo.repaint();
                System.out.println("Disparo position: " + disparo.getX() + ", " + disparo.getY());
    
                // Check if the Disparo object has gone off the top of the screen
                if (disparo.getY() > getParent().getHeight()) {
                    getParent().remove(disparo);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();

        podeAtirar = false; // Set the canShoot variable to false
        Timer timerCooldown = new Timer(disparo.getCooldown(), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                podeAtirar = true; // Set the canShoot variable back to true
                ((Timer) e.getSource()).stop();
            }
        });
        timerCooldown.start();
        System.out.println("Tiro completo");
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
