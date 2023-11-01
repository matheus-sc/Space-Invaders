package src;

public class Player extends Nave {

    public Player(int vida) {
        setVida(vida);  // Inicializa a vida do player

        setX(950);  // Inicializa a posição x do player
        setY(900);;  // Inicializa a posição y do player
        setSprite("C:\\Users\\mathe\\Downloads\\SpaceInvaders\\assets\\Jogador.png");  // Inicializa o sprite do player

    }

    // Método da interface Nave para controlar o disparo do player
    public void atirar(int dano, int velocidade, int cooldown) {
        
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
