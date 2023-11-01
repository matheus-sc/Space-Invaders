package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Player extends Nave {

    public Player(int vida) {
        setVida(vida);  // Inicializa a vida do player

        setX(950);  // Inicializa a posição x do player
        setY(900);;  // Inicializa a posição y do player
        setSprite("assets/Jogador.png");  // Inicializa o sprite do player

    }

    // Método da interface Nave para controlar o disparo do player
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
