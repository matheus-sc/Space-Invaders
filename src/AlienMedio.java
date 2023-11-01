package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class AlienMedio extends Nave {
    private int velocidade;

    public AlienMedio() {
        setVida(20);
        this.velocidade = 10;

       setSprite("assets/AlienMedio.png");
    }

    public boolean atirar(int dano, int velocidade, int cooldown) {
        if (!getPodeAtirar()) {
            return false;
        }

        Disparo disparo = new Disparo(dano, velocidade, cooldown, "assets/TiroMedio.png", this);
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

    public void sofreuDano() {
        if (getVida() < 20) setSprite("assets/AlienMedioDano.png");
    }

    public void moverEsquerda() {
        if (getX() > 0) {
            setX(getX() - velocidade);
        }
    }

    public void moverDireita() {
        if (getX() < getFundoWidth() - getWidth()) {
            setX(getX() + velocidade);  // move 10 units to the right
        }
    }
}
