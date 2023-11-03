package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Player extends Nave {

    public Player(int vida) {
        setVida(vida);  // Inicializa a vida do player

        setSprite("assets/Jogador.png");  // Inicializa o sprite do player
        int screenWidth = getScreenWidth();
        int screenHeight = getScreenHeight();
        int playerWidth = getWidth();
        int playerHeight = getHeight();
    
        int playerX = (screenWidth - playerWidth) / 2;
        int playerY = screenHeight - playerHeight - 50;
        setX(playerX);
        setY(playerY);
    }

    public boolean atirar(int dano, int velocidade, int cooldown, String spriteTiroPath) {
        if (!getPodeAtirar()) {
            return false;
        }

        Disparo disparo = new Disparo(dano, velocidade, cooldown, spriteTiroPath, this);
        setDisparos(disparo);
        setPodeAtirar(false);
        Timer timer = new Timer(cooldown, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setPodeAtirar(true);
            }
        });
        timer.setRepeats(false);
        timer.start();
        return true;
    }

    public void moverEsquerda() {
        if (getX() > 0) {
            setX(getX() - 10);
        }
    }

    public void moverDireita() {
        int playerWidth = getWidth();
        int screenWidth = getScreenWidth();
        if (getX() < screenWidth - playerWidth) {
            setX(getX() + 10);
        }
    }
}