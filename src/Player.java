package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Player extends Nave {

    public Player(int vida) {
        setVida(vida);  // Inicializa a vida do player

        setSprite("assets/Jogador.png");  // Inicializa o sprite do player
        int playerX = (getFundoWidth() - getWidth()) / 2;
        int playerY = getFundoHeight() - getHeight() - 50;
        setX(playerX);
        setY(playerY);
    }

    public boolean atirar(int dano, int velocidade, int cooldown) {
        if (!getPodeAtirar()) {
            return false;
        }

        Disparo disparo = new Disparo(dano, velocidade, cooldown, this);
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
        if (getX() < getFundoWidth() - getWidth()) {
            setX(getX() + 10);  // move 10 units to the right
        }
    }
}
