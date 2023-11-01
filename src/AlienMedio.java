package src;

public class AlienMedio extends Nave {
    private int velocidade;

    public AlienMedio() {
        setVida(20);
        this.velocidade = 10;

       setSprite("C:\\Users\\mathe\\Downloads\\SpaceInvaders\\assets\\AlienMedio.png");
    }

    public void atirar(int dano, int velocidade, int cooldown) {
        
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
