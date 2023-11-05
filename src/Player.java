package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

// Classe Player que herda de Nave
public class Player extends Nave {

    public Player(int vida) {
        setVida(vida);  // Inicializa a vida do player

        setSprite("/assets/Jogador.png");  // Inicializa o sprite do player

        // Obtém o tamanho da tela em tempo de execução para centralizar o player
        int screenWidth = getScreenWidth();
        int screenHeight = getScreenHeight();
        int playerWidth = getWidth();
        int playerHeight = getHeight();
    
        int playerX = (screenWidth - playerWidth) / 2;
        int playerY = screenHeight - playerHeight - 50;
        setX(playerX);
        setY(playerY);
    }

    // Método para atirar
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
        timer.setRepeats(false);
        timer.start();
        return true;
    }

    // Métodos de movimentação
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