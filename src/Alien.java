package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

// Classe abstrata para os aliens
public abstract class Alien extends Nave {
    private int velocidade;  // Velocidade do alien

    // Método para setar a velocidade do alien
    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    // Método para o disparo do Alien
    public boolean atirar(int dano, int velocidade, int cooldown, String spriteTiroPath) {
        if (!getPodeAtirar()) {  // Se o atributo podeAtirar for falso, retorna falso
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
        timer.start();
        return true;
    }

    // Métodos de movimentação (utilizam a velocidade do alien em questão)
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