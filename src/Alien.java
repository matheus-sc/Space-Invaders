package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public abstract class Alien extends Nave {
    private int velocidade;

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
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
            setX(getX() - velocidade);
        }
    }

    public void moverDireita() {
        if (getX() < getScreenWidth() - getWidth()) {
            setX(getX() + velocidade);  // move 10 units to the right
        }
    }

    public void moverBaixo() {
        setY(getY() + 30);
    }
}